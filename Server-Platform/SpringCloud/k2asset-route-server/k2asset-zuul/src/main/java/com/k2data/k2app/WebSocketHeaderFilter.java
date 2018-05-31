//package com.k2data.k2app;
//
//import com.netflix.zuul.ZuulFilter;
//import com.netflix.zuul.context.RequestContext;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * @author lidong9144@163.com 17-5-12.
// */
//@Component
//public class WebSocketHeaderFilter extends ZuulFilter {
//
//    @Override
//    public String filterType() {
//        return "pre";
//    }
//
//    @Override
//    public int filterOrder() {
//        return 999;
//    }
//
//    @Override
//    public boolean shouldFilter() {
//        return true;
//    }
//
//    @Override
//    public Object run() {
//        RequestContext ctx = RequestContext.getCurrentContext();
//        HttpServletRequest request = ctx.getRequest();
//
//        String upgradeHeader = request.getHeader("Upgrade");
//        if (upgradeHeader == null) {
//            upgradeHeader = request.getHeader("upgrade");
//        }
//
//        if (upgradeHeader != null && "websocket".equalsIgnoreCase(upgradeHeader)) {
//            ctx.addZuulRequestHeader("connection", "Upgrade");
//        }
//
//        return null;
//    }
//
//}
