package com.lessayer.controller;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	public String showUserList() {
		
		return "userlist";
		
	}
	
	@GetMapping("/send-mail")
	public String sendMail() {
		
		// mailService.sendSimpleMessage("test", "test", "test");
		return "mail";
		
	}
	
	
	@ModelAttribute("users")
	public List<User> populateAllUsers() {
		
		List<User> admins = userService.retrieveAllAdmins();
		List<User> users = userService.retrieveAllUsers();
		List<User> allUsers = Stream.concat(admins.stream(), users.stream())
									.toList();
		return allUsers;
		
	}
	
}
