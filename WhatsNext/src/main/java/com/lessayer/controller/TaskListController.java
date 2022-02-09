package com.lessayer.controller;

import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.memory.UserAttribute;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lessayer.entity.Priority;
import com.lessayer.entity.Task;
import com.lessayer.service.TaskListService;



@Controller
public class TaskListController {
	
	@Autowired
	private TaskListService service;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("/task-list")
	public String shwoTaskList() {
		
		return "tasklist";
		
	}
	
	@GetMapping("/add-task")
	public String showAddTaskPage() {
		
		return "updatetask";
		
	}
	
	@PostMapping("/add-task")
	public String createTask(@RequestParam String title, @RequestParam String description,
			@RequestParam String startDate, @RequestParam String endDate,
			@RequestParam String priority) {
		
		service.createTask(title, description, 
				Date.valueOf(startDate), Date.valueOf(endDate), Priority.valueOf(priority));
		return "redirect:/task-list";
		
	}
	
	@GetMapping("/delete-task")
	public String deleteTask(@RequestParam Long taskId) {
		
		service.deleteTask(taskId);
		return "redirect:/task-list";
		
	}
	
	@GetMapping("/update-task")
	public String showUpdateTask(@RequestParam Long taskId, ModelMap model) {
		
		Task task = service.retrieveTaskById(taskId).get();
		model.put("updateTask", task);
		service.deleteTask(taskId);
		return "updatetask";
		
	}
	
	
	@ModelAttribute("tasks")
	public List<Task> populateTasks(Principal principal) {
		service.setUserId(2L);
		logger.info("Current user is {}", principal.getName());
		return service.retrieveTasks().get();
		
	}
}
