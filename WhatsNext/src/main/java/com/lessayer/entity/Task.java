package com.lessayer.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name="find_all_tasks", query="select p from Task p")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long taskId;
	private long userId;
	private String title;
	private String description;
	private Date startDate;
	private Date endDate;
	private Priority priority;
	
	public Task() {
		
	}
	
	public Task(long userId, String title, String description, Date startDate, Date endDate,
			Priority priority) {
		super();
		this.userId = userId;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.priority = priority;
	}
	
	public Task(long taskId, long userId, String title, String description, Date startDate, Date endDate,
			Priority priority) {
		super();
		this.taskId = taskId;
		this.userId = userId;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.priority = priority;
	}
	
	@Override
	public String toString() {
		
		return String.format("Task ID %d\n"
				+ "  user ID=%d\n"
				+ "  title=%s\n"
				+ "  descriptio=%s\n"
				+ "  state date=%s\n"
				+ "  end date=%s\n"
				+ "  priority=%s\n",
				taskId, userId, title, description, startDate, endDate, priority);
		
	}

	public long getTaskId() {
	
		return taskId;
	}

	public void setTaskId(long taskId) {
	
		this.taskId = taskId;
	}

	public long getUserId() {
	
		return userId;
	}

	public void setUserId(long userId) {
	
		this.userId = userId;
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

	public Date getStartDate() {
	
		return startDate;
	}

	public void setStartDate(Date startDate) {
	
		this.startDate = startDate;
	}

	public Date getEndDate() {
	
		return endDate;
	}

	public void setEndDate(Date endDate) {
	
		this.endDate = endDate;
	}

	public Priority getPriority() {
	
		return priority;
	}

	public void setPriority(Priority priority) {
	
		this.priority = priority;
	}

	
}
