package com.lessayer.service;


import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lessayer.entity.Priority;
import com.lessayer.entity.Task;


@SpringBootTest
class TaskListServiceTests {
	
	static private Logger logger = LoggerFactory
			.getLogger(TaskListServiceTests.class);
	
	@Autowired
	private TaskListService service;
	private Long userId = 2L;
	
	@BeforeEach
	void setTestConfiguration() {
		
		service.setUserId(userId);
		
	}
	
	@Test
	void retrieveTasksTest() {
		
		if(service.retrieveTasks().isPresent()) {
			
			logger.info("Tasks with user id {}: {}", userId, 
					service.retrieveTasks().get());
			
		}
		else {
			
			logger.info("No task with user id {}.", userId);
			
		}
		
	}
	
	@Test
	void retrieveTaskByIdTest() {
		
		Long taskId = service.retrieveTasks().get().get(0).getTaskId();
		if(service.retrieveTaskById(taskId).isPresent()) {
			
			logger.info("Task id {}: {}", taskId, 
					service.retrieveTaskById(taskId).get());
			
		}
		else {
			
			logger.info("No task with task id {}.", taskId);
			
		}
		
	}
	
	@Test
	void createUpdateAndDeleteTaskTest() {
		
		String title = "Learn Spring Boot";
		String description = "Can establish java project with Spring Boot easily";
		Date startDate = Date.valueOf("2021-12-31");
		Date endDate = Date.valueOf("2022-03-20");
		Priority priority = Priority.Critical;
		
		Long taskId = service.createTask(title, description, 
									startDate, endDate, priority);
		logger.info("Successfully create task. The task ID is {}.", taskId);
		logger.info("The content of created task: {}", service.retrieveTaskById(taskId));
		
		
		Task updateTask = service.retrieveTaskById(taskId).get();
		logger.info("Before update. Task id {}: {}", taskId, updateTask);
		updateTask.setTitle("Learn French grammar");
		updateTask.setDescription("Test update");
		
		service.updateTask(updateTask.getTaskId(), updateTask.getTitle(), 
					updateTask.getDescription(), updateTask.getStartDate(),
					updateTask.getEndDate(), updateTask.getPriority());
		
		logger.info("After update. Task id {}: {}", taskId, 
				service.retrieveTaskById(taskId).get());
		
		
		logger.info("Before delete task id {}: {}", taskId, service.retrieveTasks());
		service.deleteTask(taskId);
		logger.info("After delete task id {}: {}", taskId, service.retrieveTasks());
		
	}
	
	@Test
	void sortTasksByIdTest() {
		
		logger.info("Before sort by id: {}", service.retrieveTasks());
		logger.info("After sort in ascending order by id: {}", service.sortTasksById(true));
		logger.info("After sort in descending order by id: {}", service.sortTasksById(false));
		
	}
	
	@Test
	void sortTasksByTitleTest() {
		
		logger.info("Before sort by id: {}", service.retrieveTasks());
		logger.info("After sort in ascending order by id: {}", 
				service.sortTasksByTitle(true));
		logger.info("After sort in descending order by id: {}", 
				service.sortTasksByTitle(false));
		
	}
	
	@Test
	void sortTasksByStartDateTest() {
		
		logger.info("Before sort by id: {}", service.retrieveTasks());
		logger.info("After sort in ascending order by id: {}", 
				service.sortTasksByStartDate(true));
		logger.info("After sort in descending order by id: {}", 
				service.sortTasksByStartDate(false));
	
	}
	
	@Test
	void sortTasksByEndDateTest() {
		
		logger.info("Before sort by id: {}", service.retrieveTasks());
		logger.info("After sort in ascending order by id: {}", 
				service.sortTasksByEndDate(true));
		logger.info("After sort in descending order by id: {}", 
				service.sortTasksByEndDate(false));
		
	}
	
	@Test
	void sortTasksByPriorityTest() {
		
		logger.info("Before sort by id: {}", service.retrieveTasks());
		logger.info("After sort in ascending order by id: {}", 
				service.sortTasksByPriority(true));
		logger.info("After sort in descending order by id: {}", 
				service.sortTasksByPriority(false));
		
	}
	
}
