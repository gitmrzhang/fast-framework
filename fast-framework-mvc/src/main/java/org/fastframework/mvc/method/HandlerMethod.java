package org.fastframework.mvc.method;

import java.lang.reflect.Method;

import org.fastframework.util.Assert;

public class HandlerMethod {
	
	private final Class<?> classType;

	private final Method method;
	
	public HandlerMethod(Class<?> clazz,Method method){
		Assert.notNull(clazz, "Bean is required");
		Assert.notNull(method, "Method is required");
		this.classType=clazz;
		this.method=method;
	}

	public Class<?> getClassType() {
		return classType;
	}

	public Method getMethod() {
		return method;
	}

}
