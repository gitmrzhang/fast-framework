package org.fastframework.mvc.method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fastframework.mvc.HandlerAdapter;
import org.fastframework.mvc.ModelAndView;

public abstract class AbstractHandlerMethodAdapter implements HandlerAdapter {

	@Override
	public boolean supports(Object handler) {
		return (handler instanceof HandlerMethod);
	}

	@Override
	public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return handleInternal(request, response, (HandlerMethod) handler);
	}

	protected abstract ModelAndView handleInternal(HttpServletRequest request, HttpServletResponse response,
			HandlerMethod handler) throws Exception;

}
