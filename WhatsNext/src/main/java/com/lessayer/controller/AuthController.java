package com.lessayer.controller;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.lessayer.entity.Role;
import com.lessayer.service.UserService;

@Controller
@SessionAttributes({ "userName", "isAdmin" })
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	private Date loggedInDate;
	
	@GetMapping("/login")
	public String login(@RequestParam(required = false, name = "registerSuccess") Boolean registerSuccess,
			ModelMap model) {
		
		if (registerSuccess != null) {
			model.addAttribute("registerSuccess", true);
		}
		loggedInDate = Date.valueOf(LocalDate.now());
		
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
	
	@GetMapping("/forgetPassword")
	public String showForgetPasswordPage(@RequestParam(required = false) Boolean userNotExisted, ModelMap model) {
		
		if (userNotExisted != null) {
			model.addAttribute("userNotExisted", true);
		}
		
		return "forgetpassword";
	}
	
	@PostMapping("/resetPassword")
	public String resetPassword(@RequestParam(name = "username") String userName,
			@RequestParam(name = "password") String password, ModelMap model) {
		
		try {
			userService.updatePassword(userName, password);
		}
		catch (Exception e) {
			return "redirect:/forgetPassword?userNotExisted=true";
		}
		
		return "redirect:/login";
	}
	
	@GetMapping("/logout")
	public String logout() {
		
		return "logout";

	}
	
	@ResponseBody
	@GetMapping("/updateLastLoginDate")
	public String updateLastLoginDate() {
		
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		userService.updateLastLoginDate(userName, loggedInDate);
		
		return "Update finished";
		
	}
	
}
