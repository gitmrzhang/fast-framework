package org.fastframework.mvc.view;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fastframework.mvc.View;

public abstract class AbstractView implements View {
	/**
	 * 在子类中可以重载，可以改变contentType
	 */
	public static final String DEFAULT_CONTENT_TYPE = "text/html;charset=utf-8";

	private String contentType = DEFAULT_CONTENT_TYPE;

	public void setContentType(String contentType){
		this.contentType=contentType;
	}
	@Override
	public String getContentType() {
		return this.contentType;
	}

	@Override
	public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 合并参数 并绑定view
		Map<String, Object> mergedModel = createMergedOutputModel(model, request, response);
		// 下载处理
		prepareResponse(request, response);
		// response 输出
		renderMergedOutputModel(mergedModel, request, response);
	}

	protected Map<String, Object> createMergedOutputModel(Map<String, ?> model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> mergedModel = new LinkedHashMap<String, Object>();
		if (model != null) {
			mergedModel.putAll(model);
		}
		return mergedModel;
	}

	protected void prepareResponse(HttpServletRequest request, HttpServletResponse response) {
		if (generatesDownloadContent()) {
			response.setHeader("Pragma", "private");
			response.setHeader("Cache-Control", "private, must-revalidate");
		}
	}

	/**
	 * 返回视图是否为下载
	 * 
	 * @return
	 */
	protected boolean generatesDownloadContent() {
		return false;
	}

	/**
	 * 子类必须实现来表现该view
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	protected abstract void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

}
