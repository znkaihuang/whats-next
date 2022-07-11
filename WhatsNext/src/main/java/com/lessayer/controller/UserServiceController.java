package com.lessayer.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	private Integer pageNum;
	private Boolean ascending;
	
	@GetMapping("/user-list")
	public String showUserList(ModelMap model) {
		return "redirect:/user-list/1";
	}
	
	@GetMapping("/user-list/{pageNum}")
	public String showUserList(ModelMap model,
		@PathVariable(name = "pageNum") Integer pageNum,
		@RequestParam(required = false) Boolean ascending) {
		
		this.pageNum = pageNum;
		this.ascending = ascending;
		
		List<User> userList;
		if (ascending == null) {
			userList = populateAllUsers(pageNum);
		}
		else {
			userList = populateAllUsers(pageNum, ascending);
		}
		
		model.put("users", userList);
		model.put("currentPage", "user-list");
		model.put("pageNum", pageNum);
		model.put("totalPage", userService.getTotalPage());
		model.put("ascending", ascending);
		
		return "userlist";
		
	}
	
	@GetMapping("/send-mail/{userId}")
	public String showSendMailForm(@PathVariable("userId") Long userId, ModelMap model) {
		
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		model.put("senderEmail", userService.retrieveUserByName(userName).get().getEmail());
		model.put("receiverEmail", userService.retrieveUserById(userId).get().getEmail());
		model.put("currentPage", "user-list");
		model.put("pageNum", pageNum);
		model.put("ascending", ascending);
		
		return "mail";
		
	}
	
	@PostMapping("/send-mail")
	public String sendMail(@RequestParam(name = "senderEmail") String senderEmail, 
			@RequestParam(name = "receiverEmail") String receiverEmail, @RequestParam(name = "emailSubject") String emailSubject, 
			@RequestParam(name = "emailContent") String emailContent, ModelMap model) {
		
		mailService.sendSimpleMessage(receiverEmail, emailSubject, emailContent);
		String redirectURLsuffix = (ascending == null) ? "" : "?ascending=" + ascending.toString();
		
		return "redirect:/user-list/" + pageNum.toString() + redirectURLsuffix;
		
	}
	
	@GetMapping("/toggle-role/{userId}")
	public String toggleRole(@PathVariable(name = "userId") Long userId) {
		
		userService.toggleRole(userId);
		String redirectURLsuffix = (ascending == null) ? "" : "?ascending=" + ascending.toString();
		
		return "redirect:/user-list/" + pageNum.toString() + redirectURLsuffix;
	}
	
	@GetMapping("/delete-user/{userId}")
	public String deleteUser(@PathVariable(name = "userId") Long userId) {
		
		userService.deleteUser(userId);
		String redirectURLsuffix = (ascending == null) ? "" : "?ascending=" + ascending.toString();
		
		if (userService.getTotalUserNum() % userService.USER_PER_PAGE == 1 && pageNum > 1) {
			pageNum--;
		}
		
		return "redirect:/user-list/" + pageNum.toString() + redirectURLsuffix;
	}
	
	public List<User> populateAllUsers(Integer pageNum) {
		
		Optional<List<User>> userListOptional = userService.retrieveAllUsersByPage(pageNum);
		return userListOptional.get();
		
	}
	
	public List<User> populateAllUsers(Integer pageNum, Boolean ascendingOrder) {
		
		Optional<List<User>> userListOptional = userService.retrieveAllUsersByPage(pageNum, ascendingOrder);
		return userListOptional.get();
		
	}
}
