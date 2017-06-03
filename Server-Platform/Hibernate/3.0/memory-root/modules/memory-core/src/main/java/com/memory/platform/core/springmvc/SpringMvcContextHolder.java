package com.memory.platform.core.springmvc;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * springmvc与spring的都有一个上下文，他们之间是父子关系。
 * springmvc的上下文可以访问spring上下文的所有bean。
 * 而作为父上下文的spring则不能访问springmvc的bean。
 * Spring的上下文是由“org.springframework.web.context.ContextLoaderListener”负责创建，
 * springmvc的上下文是由“org.springframework.web.servlet.DispatcherServlet”负责创建，
 * 且可以有多个DispatcherServlet
 * @author 
 */
public class SpringMvcContextHolder {
	
	public SpringMvcContextHolder() {
	}
	
	/**
	 * 取得SpringMVC的ApplicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		ApplicationContext applicationContext = RequestContextUtils.getWebApplicationContext(request);
		return applicationContext;
	}

	/**
	 * 从SpringMVC的ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		return (T) getApplicationContext().getBean(name);
	}

	/**
	 * 从SpringMVC的ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz) {
		return (T) getApplicationContext().getBeansOfType(clazz);
	}
}