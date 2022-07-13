package com.lessayer.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.lessayer.entity.User;
import com.lessayer.service.UserService;

@Controller
@SessionAttributes({ "userName", "isAdmin" })
public class SettingController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/settings")
	public String showWelcomePage(ModelMap model) {

		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.retrieveUserByName(userName).get();
		
		model.put("userName", userName);
		model.put("userEmail", user.getEmail());
		model.put("taskNumInHomePage", user.getTaskNumInHomePage());
		model.put("currentPage", "settings");

		return "settings";

	}
	
	@ResponseBody
	@PostMapping("/updateEmail")
	public String updateUserEmail(@RequestBody String newEmail) {
		
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		userService.updateEmail(userName, newEmail);
		
		return "Update user email successfully.";
	}
	
	@ResponseBody
	@PostMapping("/updatePassword")
	public String updateUserPassword(@RequestBody String newPassword) {
		
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		userService.updatePassword(userName, newPassword);
		
		return "Update user password successfully. <br> The update will take effect at the next log in.";
	}
	
	@ResponseBody
	@GetMapping("/changeTaskNumInHomePage")
	public String changeTaskNumInHomePage(Integer taskNum) {
		
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		userService.updateTaskNumInHomePage(userName, taskNum);
		
		return "Change displayed task number in home page successfully.";
	}
	
}