package com.lessayer;

import java.util.List;

import com.lessayer.entity.Task;

public class TaskDiv {
	
	private String name;
	private String style;
	private List<Task> taskList;
	
	public TaskDiv(String name, String style) {
		this.setName(name);
		this.setStyle(style);
	}
	
	public TaskDiv(String name, String style, List<Task> taskList) {
		this.setName(name);
		this.setStyle(style);
		this.setTaskList(taskList);
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

	public List<Task> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<Task> taskList) {
		this.taskList = taskList;
	}
	
}