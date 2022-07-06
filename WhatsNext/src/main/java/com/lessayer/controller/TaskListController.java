package com.lessayer.controller;

import java.security.Principal;
import java.sql.Date;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.lessayer.entity.Priority;
import com.lessayer.entity.Task;
import com.lessayer.entity.TaskStatus;
import com.lessayer.service.TaskListService;
import com.lessayer.service.UserService;



@Controller
@SessionAttributes({"userName", "isAdmin"})
public class TaskListController {
	
	@Autowired
	private TaskListService taskService;
	@Autowired
	private UserService userService;
	
	private Integer pageNum;
	
	@GetMapping("/task-list")
	public String shwoTaskList(ModelMap model, Principal principal) {
		
		return "redirect:/task-list/1";
		
	}
	
	@GetMapping("/task-list/{pageNum}")
	public String showTaskListByPage(ModelMap model, Principal principal,
		@PathVariable(name = "pageNum") Integer pageNum) {
		
		this.pageNum = pageNum;
		
		model.put("tasks", populateTasks(principal, pageNum));
		model.put("currentPage", "task-list");
		model.put("pageNum", pageNum);
		model.put("totalPage", populateTotalPage());

		return "tasklist";
		
	}
	
	@GetMapping("/add-task")
	public String showAddTaskPage(ModelMap model) {
		
		model.put("taskFormTitle", "New Task");
		model.put("currentPage", "task-list");
		
		return "taskform";
		
	}
	
	@PostMapping("/add-task")
	public String createTask(String taskId, String userId,
			@RequestParam String title, @RequestParam String description,
			@RequestParam String startDate, @RequestParam String endDate,
			@RequestParam String priority, @RequestParam String status) {
		
		System.out.println(description);
		if (!taskId.isEmpty()) {
			taskService.updateTask(Long.valueOf(taskId), title, description,
				Date.valueOf(startDate), Date.valueOf(endDate), Priority.valueOf(priority), TaskStatus.valueOf(status));
			
			return "redirect:/task-list/" + pageNum;
		}
		else {
			taskService.createTask(title, description, 
				Date.valueOf(startDate), Date.valueOf(endDate), Priority.valueOf(priority), TaskStatus.valueOf(status));
			
			Integer totalTaskNum = taskService.getTotalTaskNum();
			Integer totalPage = taskService.getTotalPage();
			
			if ((totalTaskNum + 1) % taskService.TASKS_PER_PAGE == 1) {
				totalPage++;
			}
			
			return "redirect:/task-list/" + totalPage;
		}
		
	}
	
	@GetMapping("/delete-task")
	public String deleteTask(@RequestParam Long taskId) {
		
		taskService.deleteTask(taskId);
		Integer taskNum = taskService.getTotalTaskNum();
		Integer totalPage = taskService.getTotalPage();
		if (((taskNum - 1) % taskService.TASKS_PER_PAGE) == 0 && pageNum == totalPage && pageNum != 1) {
			pageNum--;
		}
		
		return "redirect:/task-list/"  + pageNum;
		
	}
	
	@GetMapping("/update-task")
	public String showUpdateTask(@RequestParam Long taskId, ModelMap model) {
		
		Task task = taskService.retrieveTaskById(taskId).get();
		
		model.put("updateTask", task);
		model.put("taskFormTitle", "Update Task");
		model.put("currentPage", "task-list");
		model.put("pageNum", pageNum);
		
		return "taskform";
		
	}
	
	public List<Task> populateTasks(Principal principal, Integer pageNum) {
		
		Long userId = userService.retrieveUser(principal.getName()).get().getUserId();
		taskService.setUserId(userId);
		
		return taskService.retrieveTasksByPage(pageNum).get();
	}
	
	public Integer populateTotalPage() {
		return taskService.getTotalPage();
	}
	
}
