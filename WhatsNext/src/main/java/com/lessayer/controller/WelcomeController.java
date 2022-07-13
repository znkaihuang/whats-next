package com.lessayer.controller;

import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;
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

import com.lessayer.TaskDiv;
import com.lessayer.entity.Priority;
import com.lessayer.entity.Role;
import com.lessayer.entity.Task;
import com.lessayer.service.TaskListService;
import com.lessayer.service.UserService;

@Controller
@SessionAttributes({ "userName", "isAdmin" })
public class WelcomeController {

	@Autowired
	private UserService userService;

	@Autowired
	private TaskListService taskListService;

	private List<TaskDiv> taskDivs = new ArrayList<>();
	
	@GetMapping("/")
	public String showWelcomePage(ModelMap model) {

		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		String userName = authentication.getName();
		Boolean isAdmin = (userService.retrieveUserByName(userName).get().getRole() == Role.ADMIN) ? true : false;
		
		model.put("userName", userName);
		model.put("isAdmin", isAdmin);
		model.put("currentPage", "home");

		return "welcome";

	}
	
	@ModelAttribute("taskDivs")
	public List<TaskDiv> populateTaskDivs(Principal principal) {
		
		Long userId = userService.retrieveUserByName(principal.getName()).get().getUserId();
		
		if (taskDivs.isEmpty()) {
			taskDivs.add(new TaskDiv("Important Urgent Tasks", "first-task-div",
					populateTaskListForTaskDivs(userId, "Important Urgent Tasks")));
			taskDivs.add(new TaskDiv("Important Not Urgent Tasks", "second-task-div",
					populateTaskListForTaskDivs(userId, "Important Not Urgent Tasks")));
			taskDivs.add(new TaskDiv("Not Important Urgent Tasks", "third-task-div",
					populateTaskListForTaskDivs(userId, "Not Important Urgent Tasks")));
			taskDivs.add(new TaskDiv("Not Important Not Urgent Tasks", "forth-task-div",
					populateTaskListForTaskDivs(userId, "Not Important Not Urgent Tasks")));
		}
		else {
			taskDivs.get(0).setTaskList(populateTaskListForTaskDivs(userId, "Important Urgent Tasks"));
			taskDivs.get(1).setTaskList(populateTaskListForTaskDivs(userId, "Important Not Urgent Tasks"));
			taskDivs.get(2).setTaskList(populateTaskListForTaskDivs(userId, "Not Important Urgent Tasks"));
			taskDivs.get(3).setTaskList(populateTaskListForTaskDivs(userId, "Not Important Not Urgent Tasks"));
		}

		return taskDivs;
	}

	public List<Task> populateTaskListForTaskDivs(Long userId, String populateType) {

		Priority[] importantPriority = { Priority.CRITICAL, Priority.HIGH };
		Priority[] notImportantPriority = { Priority.MEDIUM, Priority.LOW };
		List<Task> importantTasks = taskListService
				.filterTaskListWithActiveStatus(taskListService.retrieveTasksByPriorities(userId, importantPriority).get());
		List<Task> notImportantTasks = taskListService
				.filterTaskListWithActiveStatus(taskListService.retrieveTasksByPriorities(userId, notImportantPriority).get());

		Long urgentThresholdDay = 7L;
		Date date = Date.valueOf(LocalDate.now().minusDays(urgentThresholdDay));

		switch (populateType) {
		case "Important Urgent Tasks":
			return taskListService.filterTaskListBeforEndDate(importantTasks, date, true);
		case "Important Not Urgent Tasks":
			return taskListService.filterTaskListAfterEndDate(importantTasks, date, false);
		case "Not Important Urgent Tasks":
			return taskListService.filterTaskListBeforEndDate(notImportantTasks, date, true);
		case "Not Important Not Urgent Tasks":
			return taskListService.filterTaskListAfterEndDate(notImportantTasks, date, false);
		default:
			return null;
		}

	}

}