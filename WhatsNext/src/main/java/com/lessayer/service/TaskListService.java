package com.lessayer.service;

import java.sql.Date;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lessayer.entity.Priority;
import com.lessayer.entity.Task;
import com.lessayer.repository.TaskRepository;

@Service
public class TaskListService {
	
	@Autowired
	private TaskRepository repository;
	
	private Long userId;
	
	public void setUserId(Long userId) {
		
		this.userId = userId;
		
	}
	
	public Optional<List<Task>> retrieveTasks() {
		
		return Optional.ofNullable(repository.findByUserId(this.userId));
		
	}
	
	// To prevent accessing other user's tasks, would check the user id first.
	public Optional<Task> retrieveTaskById(Long taskId) {
		
		Task task = repository.findByTaskId(taskId);
		if(task.getUserId() == this.userId) {
			
			return Optional.ofNullable(task);
			
		}
		else {
			
			return Optional.empty();
			
		}
		
	}

	public Long createTask(String title, String description, 
			Date startDate, Date endDate, Priority priority) {
		
		Task task = new Task(userId, title, description, startDate, endDate, priority);
		return repository.createTask(task).getTaskId();
		
	}

	public void updateTask(long taskId, String title, String description, 
			Date startDate, Date endDate, Priority priority) {
		
		Task task = new Task(taskId, userId, title, description,
								startDate, endDate, priority);
		repository.updateTask(task);
		
	}

	public void deleteTask(Long taskId) {
		
		repository.deleteTask(taskId);
		
	}
	
	private List<Task> sortTasks(Comparator<Task> comparator) {
		
		List<Task> taskList = repository.findByUserId(userId);
		taskList.sort(comparator);
		return taskList;
		
	}
	
	public List<Task> sortTasksById(Boolean ascendingOrder) {
		
		Integer factor = (ascendingOrder) ? 1 : -1;
		Comparator<Task> comparator = (Task task1, Task task2) -> 
			Long.compare(task1.getTaskId(), task2.getTaskId()) * factor;
		return sortTasks(comparator);
		
	}

	public List<Task> sortTasksByTitle(Boolean ascendingOrder) {
		
		Integer factor = (ascendingOrder) ? 1 : -1;
		Comparator<Task> comparator = (Task task1, Task task2) -> 
			task1.getTitle().compareTo(task2.getTitle()) * factor;
		return sortTasks(comparator);
		
	}

	public List<Task> sortTasksByStartDate(Boolean ascendingOrder) {
		
		Integer factor = (ascendingOrder) ? 1 : -1;
		Comparator<Task> comparator = (Task task1, Task task2) -> 
			task1.getStartDate().compareTo(task2.getStartDate()) * factor;
		return sortTasks(comparator);
		
	}
	
	public List<Task> sortTasksByEndDate(Boolean ascendingOrder) {
		
		Integer factor = (ascendingOrder) ? 1 : -1;
		Comparator<Task> comparator = (Task task1, Task task2) -> 
			task1.getEndDate().compareTo(task2.getEndDate()) * factor;
		return sortTasks(comparator);
		
	}
	
	public List<Task> sortTasksByPriority(Boolean ascendingOrder) {
		
		Integer factor = (ascendingOrder) ? 1 : -1;
		Comparator<Task> comparator = (Task task1, Task task2) -> 
			task1.getPriority().compareTo(task2.getPriority()) * factor;
		return sortTasks(comparator);
		
	}
	
}
