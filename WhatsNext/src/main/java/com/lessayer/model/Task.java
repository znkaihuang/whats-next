package com.lessayer.model;

import java.sql.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Task {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String title;
	private String description;
	private Date createTime;
	private Date deadline;
	private Priority priority;
	
	public Task() {
		
	}
	
	public Task(String title, String description, Date createTime, Date deadline, Priority priority) {
		super();
		this.title = title;
		this.description = description;
		this.createTime = createTime;
		this.deadline = deadline;
		this.priority = priority;
	}

	@Override
	public String toString() {
		
		return String.format("Task %d\n"
				+ "  title=%s\n"
				+ "  descriptio=%s\n"
				+ "  createTime=%s\n"
				+ "  deadline=%s\n"
				+ "  priority=%s\n",
				id, title, description, createTime, deadline, priority);
		
	}

	public long getId() {
	
		return id;
	}
	
	public void setId(long id) {
	
		this.id = id;
	}
	
	public String getTitle() {
	
		return title;
	}
	
	public void setTitle(String title) {
	
		this.title = title;
	}
	
	public String getDescription() {
	
		return description;
	}
	
	public void setDescription(String description) {
	
		this.description = description;
	}
	
	public Date getCreateTime() {
	
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
	
		this.createTime = createTime;
	}
	
	public Date getDeadline() {
	
		return deadline;
	}
	
	public void setDeadline(Date deadline) {
	
		this.deadline = deadline;
	}
	public Priority getPriority() {
	
		return priority;
	}
	
	public void setPriority(Priority priority) {
	
		this.priority = priority;
	}
	
}
