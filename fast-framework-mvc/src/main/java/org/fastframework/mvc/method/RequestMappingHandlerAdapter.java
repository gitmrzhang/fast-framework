package org.fastframework.mvc.method;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fast.framework.core.util.ReflectUtil;
import org.fastframework.mvc.ModelAndView;
import org.fastframework.mvc.annotation.ResponseBody;
import org.fastframework.mvc.view.json.JsonView;
import org.fastframework.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestMappingHandlerAdapter extends AbstractHandlerMethodAdapter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RequestMappingHandlerAdapter.class); 

	@Override
	protected ModelAndView handleInternal(HttpServletRequest request, HttpServletResponse response,
			HandlerMethod handler) throws Exception {
		Class<?> clazz = handler.getClassType();
		Method method = handler.getMethod();
		List<Object> controllerMethodParamList = WebUtil.getRequestParamMap(request, method.getParameterTypes());
		// ReflectUtil 获取 Controller.Method 的返回值
		Object controllerMethodResult = ReflectUtil.invokeControllerMethod(clazz,
				method,controllerMethodParamList);
		if(controllerMethodResult instanceof ModelAndView){
			return (ModelAndView) controllerMethodResult;
		}else{
			//获取方法级别上的注解
			Annotation[] annotations = method.getAnnotations();
			for(Annotation annotation : annotations){
				if(annotation instanceof ResponseBody){
					return new ModelAndView(new JsonView(controllerMethodResult));
				}
			}
			
			if(controllerMethodResult instanceof String){
				return new ModelAndView((String)controllerMethodResult);
			}
			LOGGER.debug("暂不支持返回的数据结构，返回{}", controllerMethodResult.toString());
			return null;
		}
	}

}
