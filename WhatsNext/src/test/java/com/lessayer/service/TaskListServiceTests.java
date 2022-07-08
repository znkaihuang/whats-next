package com.lessayer.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lessayer.entity.Priority;
import com.lessayer.entity.Task;
import com.lessayer.entity.TaskStatus;


@SpringBootTest
class TaskListServiceTests {
	
	static private Logger logger = LoggerFactory
			.getLogger(TaskListServiceTests.class);
	
	@Autowired
	private TaskListService realTaskListService;
	
	@Mock
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
			printTaskList(service.retrieveTasks().get());
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
		Priority priority = Priority.CRITICAL;
		TaskStatus status = TaskStatus.NEW;
		
		Long taskId = service.createTask(title, description, 
									startDate, endDate, priority, status);
		logger.info("Successfully create task. The task ID is {}.", taskId);
		logger.info("The content of created task: {}", service.retrieveTaskById(taskId));
		
		
		Task updateTask = service.retrieveTaskById(taskId).get();
		logger.info("Before update. Task id {}: {}", taskId, updateTask);
		updateTask.setTitle("Learn French grammar");
		updateTask.setDescription("Test update");
		
		service.updateTask(updateTask.getTaskId(), updateTask.getTitle(), 
					updateTask.getDescription(), updateTask.getStartDate(),
					updateTask.getEndDate(), updateTask.getPriority(), updateTask.getStatus());
		
		logger.info("After update. Task id {}: {}", taskId, 
				service.retrieveTaskById(taskId).get());
		
		
		logger.info("Before delete task id {}: {}", taskId, service.retrieveTasks());
		service.deleteTask(taskId);
		logger.info("After delete task id {}: {}", taskId, service.retrieveTasks());
		
	}
	
	@Test
	void sortTasksByIdTest() {
		
		List<Task> taskList = service.retrieveTasks().get();
		logger.info("Before sort by id: {}", service.retrieveTasks());
		
		service.sortTasksById(taskList,true);
		logger.info("After sort in ascending order by id: {}", taskList);
		
		service.sortTasksById(taskList,false);
		logger.info("After sort in descending order by id: {}", taskList);
		
	}
	
	@Test
	void sortTasksByTitleTest() {
		
		List<Task> taskList = service.retrieveTasks().get();
		logger.info("Before sort by id: {}", service.retrieveTasks());
		
		service.sortTasksByTitle(taskList,true);
		logger.info("After sort in ascending order by id: {}", 
				taskList);
		
		service.sortTasksByTitle(taskList,false);
		logger.info("After sort in descending order by id: {}", 
				taskList);
		
	}
	
	@Test
	void sortTasksByStartDateTest() {
		
		List<Task> taskList = service.retrieveTasks().get();
		logger.info("Before sort by id: {}", service.retrieveTasks());
		
		service.sortTasksByStartDate(taskList,true);
		logger.info("After sort in ascending order by id: {}", 
				taskList);
		
		service.sortTasksByStartDate(taskList,false);
		logger.info("After sort in descending order by id: {}", 
				taskList);
	
	}
	
	@Test
	void sortTasksByEndDateTest() {
		
		List<Task> taskList = service.retrieveTasks().get();
		logger.info("Before sort by id: {}", service.retrieveTasks());
		
		service.sortTasksByEndDate(taskList,true);
		logger.info("After sort in ascending order by id: {}", 
				taskList);
		
		service.sortTasksByEndDate(taskList,false);
		logger.info("After sort in descending order by id: {}", 
				taskList);
		
	}
	
	@Test
	void sortTasksByPriorityTest() {
		
		List<Task> taskList = service.retrieveTasks().get();
		logger.info("Before sort by id: {}", service.retrieveTasks());
		
		service.sortTasksByPriority(taskList,true);
		logger.info("After sort in ascending order by id: {}", 
				taskList);
		
		service.sortTasksByPriority(taskList,false);
		logger.info("After sort in descending order by id: {}", 
				taskList);
		
	}
	
	@Test
	void generateData() {
		Integer dataNum = 30;
		realTaskListService.setUserId(1L);
		for (Integer i = 0; i < dataNum; i++) {
			realTaskListService.createTask("Task " + String.valueOf(i), "For testing", 
					Date.valueOf("2022-07-07"), Date.valueOf("2022-07-10"), 
					Priority.MEDIUM, TaskStatus.NEW);
		}
	}
	
	@BeforeEach
	void generateMockData() {
		
		List<Task> taskList = new ArrayList<>();
		Integer dataNum = 30;
		for (Integer i = 0; i < dataNum; i++) {
			taskList.add(new Task(i, 0, "Task " + String.valueOf(i), "For testing", 
					Date.valueOf("2022-07-07"), Date.valueOf("2022-07-10"), 
					Priority.MEDIUM, TaskStatus.NEW));
		}
		Mockito.when(service.retrieveTasks()).thenReturn(Optional.ofNullable(taskList));
		
	}
	
	private void printTaskList(List<Task> taskList) {
		
		taskList.forEach(Task::toString);
		
	}
	
}
