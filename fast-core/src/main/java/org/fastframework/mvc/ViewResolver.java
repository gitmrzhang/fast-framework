package org.fastframework.mvc;

public interface ViewResolver {
	
	View resolveViewName(String viewName) throws Exception;

}
