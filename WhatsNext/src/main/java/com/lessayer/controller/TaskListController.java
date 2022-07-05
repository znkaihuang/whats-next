package com.lessayer.controller;

import java.security.Principal;
import java.sql.Date;
import java.util.List;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.lessayer.entity.Priority;
import com.lessayer.entity.Task;
import com.lessayer.service.TaskListService;
import com.lessayer.service.UserService;



@Controller
@SessionAttributes({"userName", "isAdmin"})
public class TaskListController {
	
	@Autowired
	private TaskListService taskService;
	@Autowired
	private UserService userService;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("/task-list")
	public String shwoTaskList(ModelMap model) {
		
		model.put("currentPage", "task-list");
		
		return "tasklist";
		
	}
	
	@GetMapping("/add-task")
	public String showAddTaskPage(ModelMap model) {
		
		model.put("currentPage", "task-list");
		
		return "updatetask";
		
	}
	
	@PostMapping("/add-task")
	public String createTask(@RequestParam String title, @RequestParam String description,
			@RequestParam String startDate, @RequestParam String endDate,
			@RequestParam String priority) {
		
		taskService.createTask(title, description, 
				Date.valueOf(startDate), Date.valueOf(endDate), Priority.valueOf(priority));
		
		return "redirect:/task-list";
		
	}
	
	@GetMapping("/delete-task")
	public String deleteTask(@RequestParam Long taskId) {
		
		taskService.deleteTask(taskId);
		
		return "redirect:/task-list";
		
	}
	
	@GetMapping("/update-task")
	public String showUpdateTask(@RequestParam Long taskId, ModelMap model) {
		
		Task task = taskService.retrieveTaskById(taskId).get();
		taskService.deleteTask(taskId);
		
		model.put("updateTask", task);
		model.put("currentPage", "task-list");
		
		return "updatetask";
		
	}
	
	
	@ModelAttribute("tasks")
	public List<Task> populateTasks(Principal principal) {
		
		Long userId = userService.retrieveUser(principal.getName()).get().getUserId();
		taskService.setUserId(userId);

		return taskService.retrieveTasks().get();
		
	}
}
