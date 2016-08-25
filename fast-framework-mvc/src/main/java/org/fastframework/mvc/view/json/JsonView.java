package org.fastframework.mvc.view.json;

import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fastframework.mvc.view.AbstractView;
import org.fastframework.util.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonView extends AbstractView {
	
	  private static final Logger LOGGER = LoggerFactory.getLogger(JsonView.class);
	
	private Object objectMapper;
	
	public JsonView() {
		setContentType("application/json");
	}
	
	public JsonView(Object objectMapper) {
		this.objectMapper = objectMapper;
		setContentType("application/json");
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		response.setContentType(getContentType());
		ServletOutputStream out = response.getOutputStream();
		String value = JSONUtil.toJSONString(objectMapper);
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("jsonView 输出：{}",value);
		}
		out.write(value.getBytes());
		out.close();
	}

}
