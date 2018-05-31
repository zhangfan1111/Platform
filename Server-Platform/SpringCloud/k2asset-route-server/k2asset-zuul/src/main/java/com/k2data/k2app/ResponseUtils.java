package com.k2data.k2app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.k2data.k2app.response.CommonResultResponse;
import com.netflix.zuul.context.RequestContext;

/**
 * @author lidong9144@163.com 17-6-13.
 */
@Component
public class ResponseUtils {
	
	private final static Logger log = LoggerFactory.getLogger(ResponseUtils.class);

    public static void setFailedRequest() {
        setFailedRequest(401, 400401, "Unauthorized");
    }

    public static void setFailedRequest(int httpCode, int code, String message) {
        log.error(message);

        CommonResultResponse<String> response = new CommonResultResponse<>();
        response.setCode(code);
        response.setMessage(message);
        response.setResult("");

        setRequest(httpCode, JSON.toJSONString(response));

        throw new AuthorizeException(message);
    }

    public static void setRequest(int httpCode, String body) {
        RequestContext ctx = RequestContext.getCurrentContext();

        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(httpCode);
        ctx.setResponseBody(body);
        ctx.addZuulResponseHeader("Content-Type", "application/json;charset=UTF-8");
    }

}
