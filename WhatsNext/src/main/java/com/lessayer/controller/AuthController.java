package com.lessayer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"userName", "isAdmin"})
public class AuthController {
	
	@GetMapping("/login")
	public String login() {
		
		return "login";
		
	}
	
	@GetMapping("logout")
	public String logout() {
		
		return "logout";
		
	}
	
}
