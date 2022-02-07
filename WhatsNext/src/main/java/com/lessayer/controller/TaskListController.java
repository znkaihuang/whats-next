package com.lessayer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lessayer.repository.TaskRepository;
import com.lessayer.service.TaskListService;


@Controller
public class TaskListController {
	
	@Autowired
	private TaskListService service;
	
	@GetMapping("/tasklist")
	@ResponseBody
	public String shwoTaskList() {
		
		return "tests";
		
	}
	
}
