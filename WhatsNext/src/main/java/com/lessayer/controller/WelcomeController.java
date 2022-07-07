package com.lessayer.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.lessayer.entity.Role;
import com.lessayer.service.UserService;

@Controller
@SessionAttributes({"userName", "isAdmin"})
public class WelcomeController {
	
	@Autowired
	UserService userService;
	
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
		
		return "welcome";
		
	}
	
}
