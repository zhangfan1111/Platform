package com.k2data.k2app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * @author lidong9144@163.com 17-6-7.
 */
@Component
public class ErrorFilter extends ZuulFilter {

	private final static Logger log = LoggerFactory.getLogger(ErrorFilter.class);
	
    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        Throwable throwable = ctx.getThrowable();

        log.error("Filter error.", throwable);

//        ctx.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//        ctx.set("error.exception", throwable.getCause());
        return null;
    }

}
