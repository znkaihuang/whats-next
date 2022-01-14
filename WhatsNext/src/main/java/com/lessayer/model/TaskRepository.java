package com.lessayer.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class TaskRepository implements Iterable<Task> {
	
	List<Task> taskList;
	
	public TaskRepository() {
		
		init();
		
	}
	
	public void init() {
		
		taskList = new ArrayList<Task>();
		
	}
	
	public boolean addTask(String title, String description, 
			Date createTime, Date deadline, Priority priority) {
		
		taskList.add(new Task(title, description, createTime, deadline, priority));
		
		return true;
		
	}
	
	public List<Task> retrieveAllTasks() {
		
		return taskList;
		
	}

	@Override
	public Iterator<Task> iterator() {
		
		return taskList.iterator();
	}
	
}
