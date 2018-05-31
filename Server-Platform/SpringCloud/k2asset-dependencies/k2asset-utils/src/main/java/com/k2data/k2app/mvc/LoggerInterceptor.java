package com.k2data.k2app.mvc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * 打印每次 request 信息的拦截器
 *
 * @author lidong9144@163.com 17-3-21.
 */
@Component
public class LoggerInterceptor implements HandlerInterceptor {

    private static final Log logger = LogFactory.getLog(LoggerInterceptor.class);

    private static final ThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<>("ThreadLocal_StartTime");

    /**
     * 请求开始时打印 uri 和所有 header 信息
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request != null) {
            if (logger.isDebugEnabled()){
                long beginTime = System.currentTimeMillis();
                startTimeThreadLocal.set(beginTime);
            }

            StringBuilder sb = new StringBuilder(request.getMethod())
                    .append(" ")
                    .append(request.getServletPath())
                    .append(" ")
                    .append(request.getQueryString())
                    .append(" sessionId: ")
                    .append(request.getRequestedSessionId());

            // 添加所有 header 信息
            Enumeration headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement().toString();
                String headerValue = request.getHeader(headerName);

                sb.append(" ").append(headerValue);
            }

            logger.info(sb.toString());
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 请求结束后打印请求消耗的时间
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (logger.isDebugEnabled() && request != null){
            long endTime = System.currentTimeMillis();

            StringBuilder sb = new StringBuilder(request.getMethod())
                    .append(" ")
                    .append(request.getServletPath())
                    .append(" cast: ")
                    .append(endTime - startTimeThreadLocal.get())
                    .append("ms");

            if (logger.isDebugEnabled()) {
                logger.debug(sb.toString());
            }
        }
    }

}
