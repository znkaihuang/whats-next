package com.lessayer.controller;

import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WelcomeController {
	
	@GetMapping("/welcome")
	public String showWelcomePage(
			@RequestParam(name = "name", defaultValue = "User", required = true) String name,
			Model model) {
		
		model.addAttribute("name", name);
		
		return "welcome";
		
	}
	
}
