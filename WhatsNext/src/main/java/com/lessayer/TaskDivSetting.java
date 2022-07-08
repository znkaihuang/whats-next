package com.lessayer;

public class TaskDivSetting {
	
	private String name;
	private String style;
	
	public TaskDivSetting(String name, String style) {
		this.setName(name);
		this.setStyle(style);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}
	
}