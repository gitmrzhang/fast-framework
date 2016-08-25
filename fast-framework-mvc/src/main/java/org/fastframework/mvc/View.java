package org.fastframework.mvc;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface View {
	
	String getContentType();
	
	void render(Map<String,?> model, HttpServletRequest request, HttpServletResponse response) throws Exception;

}
