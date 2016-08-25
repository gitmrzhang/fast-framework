package org.fastframework.mvc.view;


public abstract class AbstractUrlBasedView extends AbstractView {
	
	private String url;
	
	protected AbstractUrlBasedView(){
		
	}
	protected AbstractUrlBasedView(String url){
		this.url = url;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

}
