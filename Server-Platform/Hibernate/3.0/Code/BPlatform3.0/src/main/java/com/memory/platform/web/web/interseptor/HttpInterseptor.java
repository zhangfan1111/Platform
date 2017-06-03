package com.memory.platform.web.web.interseptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class HttpInterseptor implements HandlerInterceptor {
	private PathMatcher pathMatcher = new AntPathMatcher();
	private List<String> excludeUrls;
	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2){
		String requestPath = arg0.getServletPath();  
		if(excludeUrls != null && !"".equals(excludeUrls)) {
			for(String urlPattern : excludeUrls){  
				if(pathMatcher.match(urlPattern,requestPath)){  
					return true;  
				}
			} 
		}
		
		try {
			String url = arg0.getRequestURL().toString();
			if(url.startsWith("https")) {
				return true;
			} else {
				throw new Exception("Http is not surport!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
