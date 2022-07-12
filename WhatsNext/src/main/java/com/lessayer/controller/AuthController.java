package com.lessayer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.lessayer.entity.Role;
import com.lessayer.service.UserService;

@Controller
@SessionAttributes({ "userName", "isAdmin" })
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/login")
	public String login(@RequestParam(required = false, name = "registerSuccess") Boolean registerSuccess,
			ModelMap model) {
		
		if (registerSuccess != null) {
			model.addAttribute("registerSuccess", true);
		}
		
		return "login";

	}

	@GetMapping("/register")
	public String showRegisterForm(@RequestParam(required = false) Boolean userAlreadyExisted,
			ModelMap model) {
		
		if (userAlreadyExisted != null) {
			model.addAttribute("userAlreadyExisted", true);
		}
		
		return "register";
	}

	@PostMapping("/register")
	public String register(@RequestParam(name = "username") String userName,
			@RequestParam(name = "email") String email, @RequestParam(name = "password") String password,
			ModelMap model) {
		
		try {
			userService.createUser(userName, password, email, Role.USER);
		}
		catch (Exception e) {
			return "redirect:/register?userAlreadyExisted=true";
		}
		return "redirect:/login?registerSuccess=true";
	}

	@GetMapping("/logout")
	public String logout() {

		return "logout";

	}

}
