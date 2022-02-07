package com.lessayer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lessayer.repository.TaskRepository;

@Service
public class TaskListService {
	
	@Autowired
	private TaskRepository repository;
	
}
