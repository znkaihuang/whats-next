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
@SessionAttributes({ "userName", "isAdmin" })
public class TaskListController {

	@Autowired
	private TaskListService taskService;
	@Autowired
	private UserService userService;

	private Integer pageNum;
	private String sortField;
	private Boolean ascending;

	@GetMapping("/task-list")
	public String shwoTaskList(ModelMap model, Principal principal) {

		return "redirect:/task-list/1";

	}

	@GetMapping("/task-list/{pageNum}")
	public String showTaskListByPage(ModelMap model, Principal principal,
			@PathVariable(name = "pageNum") Integer pageNum, @RequestParam(required = false) String sortField,
			@RequestParam(required = false) Boolean ascending) {

		this.pageNum = pageNum;
		this.sortField = sortField;
		this.ascending = ascending;

		List<Task> taskList;
		if (sortField != null && ascending != null) {
			taskList = populateTasks(principal, pageNum, sortField, ascending);
		} else {
			taskList = populateTasks(principal, pageNum);
		}

		model.put("tasks", taskList);
		model.put("currentPage", "task-list");
		model.put("pageNum", pageNum);
		model.put("totalPage", taskService.getTotalPage());
		model.put("sortField", sortField);
		model.put("ascending", ascending);

		return "tasklist";

	}

	@GetMapping("/add-task")
	public String showAddTaskPage(ModelMap model) {

		model.put("taskFormTitle", "New Task");
		model.put("currentPage", "task-list");

		return "taskform";

	}

	@PostMapping("/add-task")
	public String createTask(String taskId, String userId, @RequestParam String title, @RequestParam String description,
			@RequestParam String startDate, @RequestParam String endDate, @RequestParam String priority,
			@RequestParam String status, @RequestParam(required = false) String from) {

		if (!taskId.isEmpty()) {
			taskService.updateTask(Long.valueOf(taskId), title, description, Date.valueOf(startDate),
					Date.valueOf(endDate), Priority.valueOf(priority), TaskStatus.valueOf(status));

			String urlSuffix = (sortField != null) ? "?sortField=" + sortField + "&ascending=" + ascending : "";
			
			if (from != null) {
				return "redirect:/?update=true";
			}
			else {
				return "redirect:/task-list/" + pageNum + urlSuffix;
			}
		} else {
			taskService.createTask(title, description, Date.valueOf(startDate), Date.valueOf(endDate),
					Priority.valueOf(priority), TaskStatus.valueOf(status));

////        Mark this code due to new task will always show at the top item in the first page.
//			Integer totalTaskNum = taskService.getTotalTaskNum();
//			Integer totalPage = taskService.getTotalPage();
//
//			if ((totalTaskNum + 1) % taskService.TASKS_PER_PAGE == 1) {
//				totalPage++;
//			}
//
//			return "redirect:/task-list/" + totalPage;
			return "redirect:/task-list/1";
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

		String urlSuffix = (sortField != null) ? "?sortField=" + sortField + "&ascending=" + ascending : "";

		return "redirect:/task-list/" + pageNum + urlSuffix;

	}

	@GetMapping("/update-task")
	public String showUpdateTask(@RequestParam Long taskId, @RequestParam(required = false) String from, ModelMap model) {

		Task task = taskService.retrieveTaskById(taskId).get();

		model.put("updateTask", task);
		model.put("taskFormTitle", "Update Task");
		model.put("currentPage", "task-list");
		model.put("pageNum", pageNum);
		model.put("sortField", sortField);
		model.put("ascending", ascending);
		model.put("from", from);

		return "taskform";

	}

	public List<Task> populateTasks(Principal principal, Integer pageNum) {

		Long userId = userService.retrieveUserByName(principal.getName()).get().getUserId();
		taskService.setUserId(userId);

		return taskService.retrieveTasksByPage(pageNum).get();
	}

	public List<Task> populateTasks(Principal principal, Integer pageNum, String sortField, Boolean ascending) {

		Long userId = userService.retrieveUserByName(principal.getName()).get().getUserId();
		taskService.setUserId(userId);

		return taskService.retrieveTasksByPage(pageNum, sortField, ascending).get();
	}

}
