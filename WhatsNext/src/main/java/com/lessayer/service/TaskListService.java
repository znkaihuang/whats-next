package com.lessayer.service;

import java.sql.Date;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lessayer.entity.Priority;
import com.lessayer.entity.Task;
import com.lessayer.entity.TaskStatus;
import com.lessayer.repository.TaskRepository;

@Service
public class TaskListService {
	
	@Autowired
	private TaskRepository repository;
	
	public final Integer TASKS_PER_PAGE = 8;
	private Long userId;
	private List<Task> taskListCache;
	private Boolean updateCache = false;
	private Integer totalPage;
	private Integer totalTaskNum;
	
	public void setUserId(Long userId) {
		
		this.userId = userId;
		
	}
	
	public Optional<List<Task>> retrieveTasks() {
		
		Optional<List<Task>> taskListCacheOptional = Optional.ofNullable(taskListCache);
		if (taskListCacheOptional.isEmpty() || updateCache) {
			taskListCache = repository.findByUserId(this.userId);
			totalTaskNum = taskListCache.size();
			if (totalTaskNum == 0) {
				totalPage = 0;
			}
			else if (totalTaskNum % TASKS_PER_PAGE == 0) {
				totalPage = (taskListCache.size() / TASKS_PER_PAGE);
			}
			else {
				totalPage = (taskListCache.size() / TASKS_PER_PAGE) + 1;
			}
			updateCache = false;
			
			return Optional.ofNullable(taskListCache);
		}
		else {
			return taskListCacheOptional;
		}
	}
	
	public Optional<List<Task>> retrieveTasksByPage(Integer pageNum) {
		
		Optional<List<Task>> taskListOptional = retrieveTasks();
		if (taskListOptional.isEmpty()) {
			return taskListOptional;
		}
		else {
			return Optional.ofNullable(taskListOptional.get().subList((pageNum - 1) * TASKS_PER_PAGE, 
					Math.min(taskListOptional.get().size(), pageNum * TASKS_PER_PAGE)));
		}
		
	}
	
	public Integer getTotalPage() {
		return totalPage;
	}
	
	public Integer getTotalTaskNum() {
		return totalTaskNum;
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
			Date startDate, Date endDate, Priority priority, TaskStatus status) {
		
		Task task = new Task(userId, title, description, startDate, endDate, priority, status);
		updateCache = true;
		
		return repository.createTask(task).getTaskId();
		
	}

	public void updateTask(long taskId, String title, String description, 
			Date startDate, Date endDate, Priority priority, TaskStatus status) {
		
		Task task = repository.findByTaskId(taskId);
		task.setTitle(title);
		task.setDescription(description);
		task.setStartDate(startDate);
		task.setEndDate(endDate);
		task.setPriority(priority);
		task.setStatus(status);
		repository.updateTask(task);
		updateCache = true;
		
	}

	public void deleteTask(Long taskId) {
		
		repository.deleteTask(taskId);
		updateCache = true;
		
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
	
	public List<Task> sortTasksByStatus(Boolean ascendingOrder) {
		
		Integer factor = (ascendingOrder) ? 1 : -1;
		Comparator<Task> comparator = (Task task1, Task task2) -> 
			task1.getStatus().compareTo(task2.getStatus()) * factor;
		return sortTasks(comparator);
		
	}
}
