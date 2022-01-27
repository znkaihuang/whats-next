package com.lessayer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lessayer.model.TaskRepository;


@Controller
public class TaskListController {
	
	@Autowired
	private TaskRepository taskRepository;
	
	@GetMapping("/tasklist")
	@ResponseBody
	public String shwoTaskList() {
		
		return taskRepository.findAll().toString();
		
	}
	
}
