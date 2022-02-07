package com.lessayer.entity;


import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import com.lessayer.repository.TaskRepository;

@SpringBootTest
class TaskRepositoryTests {
	
	static private Logger logger = LoggerFactory
			.getLogger(TaskRepositoryTests.class);
	static private List<Task> taskRepository;
	
	@Autowired
	private TaskRepository repository;
	
	@BeforeAll
	static void initTaskRepository() {
		
		/*
		taskRepository = new ArrayList<>();
		taskRepository.add(new Task(0, 0, "Project \"What's the next?\"", 
				"Side project to implement a scheduler application",
				Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.of(2022, 1, 31)), Priority.Critical));
		taskRepository.add(new Task(1, 0, "Project \"Account management\"", 
				"Side project to implement a account management application",
				Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.of(2022, 1, 31)), Priority.Medium));
		taskRepository.add(new Task(2, 0, "Project \"Le Petit Lecteur\"", 
				"Side project to implement a bookmark and book list application",
				Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.of(2022, 1, 31)), Priority.Medium));
		
		logger.info("Task repository: {}", taskRepository);
		*/
		
	}
	
	@Test
	void findAllTaskTest() {
		
		logger.info("Find all: {}", repository.findAllTask());
		
	}
	
	@Test
	void findByTaskIdTest() {
		
		Long taskId = 2L;
		logger.info("Find task id 2: {}", repository.findByTaskId(taskId));
		
	}
	
	@Test
	void findByUserIdTest() {
		
		Long userId = 1L;
		logger.info("Find tasks with user id 1: {}", 
				repository.findByUserId(userId));
		
	}
	
	@Test
	void createTaskTest() {
		
		Task task = new Task(2, "Learn French", "test", 
				Date.valueOf(LocalDate.now()),
				Date.valueOf("2022-03-12"),
				Priority.High);
		repository.createTask(task);
		logger.info("Create task: {}", repository.findByUserId(2));
		
	}
	
	@Test
	void deleteTaskTest() {
		
		Long userId = 2L;
		logger.info("Find tasks with user id 2: {}", 
				repository.findByUserId(userId));
		
		List<Task> deletedTasks = repository.findByUserId(userId);
		for(Task deletedTask : deletedTasks) {
			
			repository.deleteTask(deletedTask.getTaskId());
			
		}
		
		logger.info("Delete all tasks with user id 2.");
		logger.info("Find tasks with user id 2: {}", 
				repository.findByUserId(userId));
		
	}
	
	@Test
	void updateTaskTest() {
		
		Long userId = 2L;
		logger.info("Find tasks with user id 2: {}", 
				repository.findByUserId(userId));
		
		Task updatedTask = repository.findByUserId(userId).get(0);
		updatedTask.setTitle("Learn Spanish");
		repository.updateTask(updatedTask);
		
		logger.info("Update task id {}.", updatedTask.getTaskId());
		logger.info("Find tasks with user id 2: {}", 
				repository.findByUserId(userId));
		
	}
	
	@Test
	void returnTaskCountTest() {
		
		Long taskCount = repository.returnTaskCount();
		logger.info("Total {} tasks in the table.", taskCount);
		
	}
	
}
