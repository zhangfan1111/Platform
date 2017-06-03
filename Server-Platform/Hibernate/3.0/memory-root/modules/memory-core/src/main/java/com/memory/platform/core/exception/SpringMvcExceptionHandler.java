package com.memory.platform.core.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.memory.platform.common.util.ExceptionUtil;

/**
 * spring mvc异常捕获类
 * 	
 */
@Component
public class SpringMvcExceptionHandler implements HandlerExceptionResolver {

	private static final Logger logger = LoggerFactory
			.getLogger(SpringMvcExceptionHandler.class);

	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		String exceptionMessage = ExceptionUtil.getExceptionMessage(ex);
		logger.error(exceptionMessage);	
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("exceptionMessage", exceptionMessage);
		model.put("ex", ex);
		return new ModelAndView("system/errors/error", model);
	}
}
