package com.lessayer.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.lessayer.entity.User;
import com.lessayer.service.MailService;
import com.lessayer.service.UserService;

@Controller
@SessionAttributes({"userName", "isAdmin"})
public class UserServiceController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	MailService mailService;
	
	@GetMapping("/user-list")
	public String showUserList(ModelMap model) {
		
		model.put("users", populateAllUsers());
		model.put("currentPage", "user-list");
		
		return "userlist";
		
	}
	
	@GetMapping("/send-mail")
	public String sendMail() {
		
		// mailService.sendSimpleMessage("test", "test", "test");
		return "mail";
		
	}
	
	public List<User> populateAllUsers() {
		
		Optional<List<User>> userListOptional = userService.retrieveAllUsers();
		return userListOptional.get();
		
	}
	
}
