package org.fastframework.mvc;

import java.util.Map;

public class ModelAndView {

	private Object view;

	private Map<String, Object> model;

	public ModelAndView(View view, Map<String, Object> model) {
		this.view = view;
		this.model = model;
	}

	public ModelAndView(String viewName) {
		this.view = viewName;
	}

	public ModelAndView(View view) {
		this.view = view;
	}

	public void setViewName(String viewName) {
		this.view = viewName;
	}

	public String getViewName() {
		return (this.view instanceof String ? (String) this.view : null);
	}

	public void setView(View view) {
		this.view = view;
	}

	public View getView() {
		return (this.view instanceof View ? (View) this.view : null);
	}

	public void setModel(Map<String, Object> model) {
		this.model = model;
	}

	public Map<String, Object> getModel() {
		return this.model;
	}

	public boolean isReference() {
		return (this.view instanceof String);
	}

	public Map<String, Object> getModelInternal() {
		return this.model;
	}

}
