package com.lessayer.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.lessayer.TaskDivSetting;
import com.lessayer.entity.Role;
import com.lessayer.service.UserService;

@Controller
@SessionAttributes({"userName", "isAdmin"})
public class WelcomeController {
	
	@Autowired
	private UserService userService;
	
	private List<TaskDivSetting> taskDivs = new ArrayList<>();
	
	@GetMapping("/")
	public String showWelcomePage(ModelMap model) {
		
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		String userName = authentication.getName();
		Boolean isAdmin = (userService.retrieveUserByName(userName).get().getRole() == Role.ADMIN)
							? true : false;
		model.put("userName", userName);
		model.put("isAdmin", isAdmin);
		model.put("currentPage", "home");
		model.put("taskDivStyles", isAdmin);
		
		return "welcome";
		
	}
	
	@ModelAttribute("taskDivs")
	public List<TaskDivSetting> populateTaskDivs() {
		
		if (taskDivs.isEmpty()) {
			taskDivs.add(new TaskDivSetting("Important Urgent Tasks", "first-task-div"));
			taskDivs.add(new TaskDivSetting("Important Not Urgent Tasks", "second-task-div"));
			taskDivs.add(new TaskDivSetting("Not Important Urgent Tasks", "third-task-div"));
			taskDivs.add(new TaskDivSetting("Not Important Not Urgent Tasks", "forth-task-div"));
		}
		
		return taskDivs;
	}
	
}