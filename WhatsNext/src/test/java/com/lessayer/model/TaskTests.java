package com.lessayer.model;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

class TaskTests {
	
	static List<Task> taskRepository;
	
	@BeforeAll
	static void initTaskRepository() {
		
		taskRepository = new ArrayList<>();
		taskRepository.add(new Task(0, "Project \"What's the next?\"", 
				"Side project to implement a scheduler application",
				Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.of(2022, 1, 31)), Priority.Critical));
		taskRepository.add(new Task(1, "Project \"Account management\"", 
				"Side project to implement a account management application",
				Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.of(2022, 1, 31)), Priority.Medium));
		taskRepository.add(new Task(2, "Project \"Le Petit Lecteur\"", 
				"Side project to implement a bookmark and book list application",
				Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.of(2022, 1, 31)), Priority.Medium));
		
	}
	
	@Test
	void retrieveAllTasksTest() {
		
		taskRepository.forEach(task -> System.out.println(task));
		
	}

}
