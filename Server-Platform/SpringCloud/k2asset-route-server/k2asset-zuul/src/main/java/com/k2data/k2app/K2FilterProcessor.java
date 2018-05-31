package com.k2data.k2app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.FilterProcessor;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * @author lidong9144@163.com 17-6-7.
 */
public class K2FilterProcessor extends FilterProcessor {
	
	private final static Logger log = LoggerFactory.getLogger(K2FilterProcessor.class);

    @Override
    public Object processZuulFilter(ZuulFilter filter) throws ZuulException {
        try {
            return super.processZuulFilter(filter);
        } catch (ZuulException e) {
            log.error("", e);
            // 忽略这个错误，inputStream 只在登陆时读取一次
            if (e.getCause() instanceof IllegalStateException
                    && "getReader() has already been called for this request".equals(e.getCause().getMessage())) {
                return null;
            }

            RequestContext ctx = RequestContext.getCurrentContext();
            ctx.set("failed.filter", filter);
            if (!(e.getCause() instanceof AuthorizeException)) {
                ResponseUtils.setFailedRequest(500, 500000, "Server error.");
            }
            return null;
//            throw e;
        }
    }

}