package com.memory.platform.core.interceptors;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.memory.platform.core.constants.WebConstants;
import com.memory.platform.core.springmvc.WebContextUtils;

/**
 * 验证用户登陆拦截器
 * 判断是否登录，如果没有登录就重定向到/login
 * 
 * 拦截路径：/
 * 
 * @author 
 * @date 2014-6-5 下午09:02:00
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
	private List<String> excludeUrls;
	private PathMatcher pathMatcher = new AntPathMatcher();
	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	/**
	 * 在controller后拦截
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) throws Exception {
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView) throws Exception {

	}
	
	/**
	 * 在controller前拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String requestPath = request.getServletPath();  
		if(excludeUrls != null && !"".equals(excludeUrls)) {
			for(String urlPattern : excludeUrls){  
				if(pathMatcher.match(urlPattern,requestPath)){  
					return true;  
				}
			} 
		}
		
		// 如果session中没有user对象
		if (null == request.getSession().getAttribute(WebConstants.CURRENT_USER)) {
			String requestedWith = request.getHeader("x-requested-with");
			// ajax请求
			if (requestedWith != null && "XMLHttpRequest".equals(requestedWith)) {
				response.setContentType("application/json; charset=utf-8");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("session-status", "timeout");
				response.getWriter().print(WebConstants.TIME_OUT);
			} else {
				// 普通页面请求
				response.sendRedirect(request.getContextPath() + "/");
			}
			return false;
		}
		return true;

	}
	
//	@Override
//	public boolean preHandle(HttpServletRequest request,
//			HttpServletResponse response, Object handler) throws Exception {
//		HttpSession session = request.getSession();
//		String username = (String)session.getAttribute(AppStatic.WEB_APP_SESSSION_LOGINNAME);
//		if(username != null){
//			return true;
//		}else{ 
//			// 可以判断页面类型，有的请求 
//			
//			PrintWriter out = response.getWriter();
//			String accept = request.getHeader("accept"); 
//			
//			if(accept.matches(".*application/json.*")){// json数据请求 
//				out.write("{\"status\":false,\"code\":\"101\",\"message\":\"当前会话失效，请重新登录系统!\"}");
//			}
//			
//			// HTML页面
//			out.write("<script type='text/javascript'>window.location.href='login.do?status=timeout';</script>");
//			out.flush();
//			out.close();
//			return false;
//		} 
//	}
//
//	@Override
//	public void postHandle(HttpServletRequest request,
//			HttpServletResponse response, Object handler,
//			ModelAndView modelAndView) throws Exception {
//	}
//
//	@Override
//	public void afterCompletion(HttpServletRequest request,
//			HttpServletResponse response, Object handler, Exception ex)
//			throws Exception {
//	}

}
