package org.fastframework.mvc.view;

import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InternalResourceView extends AbstractUrlBasedView {

	public InternalResourceView() {

	}

	public InternalResourceView(String url) {
		setUrl(url);
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String dispatcherPath = getUrl();
		RequestDispatcher rd = getRequestDispatcher(request, dispatcherPath);
		if (rd == null) {
			throw new ServletException("Could not get RequestDispatcher for [" + getUrl()
					+ "]: Check that the corresponding file exists within your web application archive!");
		}
		rd.forward(request, response);
		return;
	}

	protected RequestDispatcher getRequestDispatcher(HttpServletRequest request, String dispatcherPath) {
		return request.getRequestDispatcher(dispatcherPath);
	}

}
