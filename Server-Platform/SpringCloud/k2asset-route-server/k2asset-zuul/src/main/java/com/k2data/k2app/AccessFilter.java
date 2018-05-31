package com.k2data.k2app;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.io.CharStreams;
import com.k2data.k2app.domain.Login;
import com.k2data.k2app.domain.SysLog;
import com.k2data.k2app.domain.UserRestResponse;
import com.k2data.k2app.response.CommonResultResponse;
import com.k2data.k2app.utils.StringUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * @author lidong9144@163.com 17-5-24.
 */
@Component
public class AccessFilter extends ZuulFilter {

	private final static Logger log = LoggerFactory.getLogger(AccessFilter.class);
	
    private static final Long expiresIn = 24 * 60 * 60L;
    private Cache<String, String> cache = CacheBuilder.newBuilder()
            .maximumSize(3000)
            .expireAfterWrite(expiresIn, TimeUnit.SECONDS)
            .build();

    private RestTemplate restTemplate;
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
    	restTemplate = new RestTemplate();
        return restTemplate;
    }
    
    @Autowired
    public RedisService redisService;
//    @Autowired
//    public AccessFilter(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return -100;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        if (request.getRequestURI().startsWith("/static")) {
            return null;
        }

        if (request.getRequestURI().equals("/v1/login")||request.getRequestURI().equals("/v2/login")) {
            if (!"POST".equals(request.getMethod())) {
                ResponseUtils.setFailedRequest(405, 400405, "Http request method not supported.");
                return null;
            }

            String username;
            String password;

            username = request.getParameter("username");
            password = request.getParameter("password");

            if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
                JSONObject bodyObj;
                try {
                    String body = CharStreams.toString(request.getReader());
                    bodyObj = JSON.parseObject(body);
                } catch (IOException e) {
                    ResponseUtils.setFailedRequest(500, 500000, "Server error.");
                    return null;
                }

                if (bodyObj == null) {
                    ResponseUtils.setFailedRequest(400, 400101, "Miss username or password.");
                    return null;
                }

                username = bodyObj.getString("username");
                password = bodyObj.getString("password");
            }

            if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
                ResponseUtils.setFailedRequest(400, 400101, "Miss username or password.");
                return null;
            }

            UserRestResponse userRestResponse = restTemplate.getForObject(
                    "http://K2ALPHA-SYS-V1/users/auth?username={username}&password={password}",
                    UserRestResponse.class,
                    username,
                    password);

            if (userRestResponse.getResult() == null || userRestResponse.getResult().getId() == null) {
                ResponseUtils.setFailedRequest(401, 400103, "Unauthorized, user not exist.");
                return null;
            }

            if (userRestResponse.getResult().getLoginFlag() == 0) {
                ResponseUtils.setFailedRequest(401, 400104, "Unauthorized, user has no permission.");
                return null;
            }

            Login login = new Login();
            login.setAccessToken(createToken());
            login.setExpiresIn(expiresIn);
            login.setUserId(userRestResponse.getResult().getId());

            CommonResultResponse<Login> response = new CommonResultResponse<>();
            response.setCode(200000);
            response.setMessage("Success");
            response.setResult(login);
            redisService.saveUser(username,login.getAccessToken());
            ResponseUtils.setRequest(200, JSON.toJSONString(response));
            try {
                insertLog(username,request.getRequestURI());
            } catch (Exception e) {
                return null;
            }
            return null;
        }

        String token = request.getHeader("X-Auth");
        if (token == null || cache.getIfPresent(token) == null) {
            redisService.tokenTimeout(token);
            ResponseUtils.setFailedRequest(401, 400102, "Unauthorized, invalid token.");
            return null;
        }else{
            if(!redisService.exitUserToken(token)){
                ResponseUtils.setFailedRequest(401, 400102, "token Invalid.");
                return null;
            }
        }
        return null;
    }

    private String createToken() {
        String token = UUID.randomUUID().toString().replace("-", "");
        cache.put(token, "1");
        return token;
    }

    private void refreshToken() {

    }
    public void insertLog(String username, String methodName) throws Exception{
        SysLog sysLog = new SysLog();
        sysLog.setModelName("k2alpha-zuul");
        sysLog.setCreateBy(username);
        sysLog.setClassName(this.getClass().getName());
        sysLog.setMethod(methodName);
        sysLog.setType("login");
        sysLog.setCreateDate(new Date());
        sysLog.setMessage("用户<"+username+">登陆");
        restTemplate.postForEntity("http://K2ALPHA-SYS-V1/logs", sysLog, Integer.class);
    }

}
