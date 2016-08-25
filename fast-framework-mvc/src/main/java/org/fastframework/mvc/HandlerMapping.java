package org.fastframework.mvc;

import org.fastframework.mvc.annotation.RequestMethod;
import org.fastframework.mvc.bean.RequestBody;
import org.fastframework.mvc.method.HandlerMethod;

import java.util.Map;

/**
 * 处理器映射
 *
 * Created by bysocket on 16/8/9.
 */
public class HandlerMapping {

	/**
	 * 处理方法体
	 *
	 * @param requestMethod
	 * @param requestPath
	 * @return
	 */
	public static HandlerMethod getHandler(String requestMethod, String requestPath) {
		HandlerMethod handler = null;
		Map<RequestBody, HandlerMethod> methodMap = ControllerCollection.getMethodMapping();
		for (Map.Entry<RequestBody, HandlerMethod> methodEntry : methodMap.entrySet()) {
			RequestBody req = methodEntry.getKey();
			String reqPath  = req.getRequestPath();
			RequestMethod reqMethod = req.getRequestMethod();
			if (reqPath.equals(requestPath) && reqMethod.name().equalsIgnoreCase(requestMethod)) {
				handler = methodEntry.getValue();
				if (handler != null) {
					return handler;
				}
			}
		}
		return handler;
	}
}
