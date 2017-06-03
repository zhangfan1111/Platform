package com.memory.platform.core.springmvc;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

public class ModelViewMethodReturnValueHandler implements HandlerMethodReturnValueHandler {

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		Class<?> paramType = returnType.getParameterType();
		return ModelView.class.isAssignableFrom(paramType);
	}

	@Override
	public void handleReturnValue(
			Object returnValue, MethodParameter returnType,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest)
			throws Exception {

		if (returnValue == null) {
			return;
		} else if (returnValue instanceof ModelView) {
			String viewName = ((ModelView) returnValue).getViewName();
			mavContainer.setViewName(viewName);
			if (isRedirectViewName(viewName)) {
				mavContainer.setRedirectModelScenario(true);
			}
		} else {
			// should not happen
			throw new UnsupportedOperationException("Unexpected return type: " +
					returnType.getParameterType().getName() + " in method: " + returnType.getMethod());
		}
	}

	/**
	 * Whether the given view name is a redirect view reference.
	 * @param viewName the view name to check, never {@code null}
	 * @return "true" if the given view name is recognized as a redirect view
	 * reference; "false" otherwise.
	 */
	protected boolean isRedirectViewName(String viewName) {
		return viewName.startsWith("redirect:");
	}

}
