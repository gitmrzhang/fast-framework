package org.fastframework.mvc.view;

import org.fastframework.mvc.View;
import org.fastframework.mvc.ViewResolver;

public class InternalResourceViewResolver implements ViewResolver {

	private String prefix;
	private String suffix;

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	@Override
	public View resolveViewName(String viewName) throws Exception {
		return new InternalResourceView(viewName);
	}

}
