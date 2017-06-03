package com.memory.platform.core.springmvc;


public class ModelView {
	private String viewName;

	public ModelView() {
		
	}
	public ModelView(String viewName) {
		this.viewName = viewName;
	}
	
	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
}
