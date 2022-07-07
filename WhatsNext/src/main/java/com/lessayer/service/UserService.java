package com.lessayer.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lessayer.entity.Role;
import com.lessayer.entity.User;
import com.lessayer.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	public Optional<List<User>> retrieveAllUsers() {
		return repository.findAllUsers();
	}
	
	public List<User> retrieveAllAdmins() {
		return repository.findByRole(Role.ADMIN);
	}
	
	public List<User> retrieveAllNormalUsers() {
		return repository.findByRole(Role.USER);
	}
	
	public Optional<User> retrieveUserByName(String userName) {
		
		return repository.findByUserName(userName);
		
	}
	
	public boolean isUserExist(String userName) {
		
		return (retrieveUserByName(userName).isPresent()) ? true : false;
		
	}
	
	public void createUser(String userName, String password, String email, Role role)
		throws RuntimeException {
		
		if(isUserExist(userName)) {
			
			throw new RuntimeException("The user has already exist!");
			
		}
		else {
			
			repository.createUser(new User(userName, password, email, role, 
					Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now())));
			
		}
		
	}
	
	public void deleteUser(String userName) {
		
		repository.deleteUser(repository.findByUserName(userName).get().getUserId());
		
	}
	
	public void updatePassword(String userName, String password) {
		
		User user = repository.findByUserName(userName).get();
		user.setPassword(password);
		repository.updateUser(user);
		
	}
	
	public void updateLastLoginDate(String userName, Date date) {
		
		User user = repository.findByUserName(userName).get();
		user.setLastLoginDate(date);
		repository.updateUser(user);
		
	}
	
	public void updateEmail(String userName, String email) {
		
		User user = repository.findByUserName(userName).get();
		user.setEmail(email);
		repository.updateUser(user);
		
	}
	
//	public List<User> sortUsersByRole(Boolean ascendingOrder) {
//		
//		Integer factor = (ascendingOrder) ? 1 : -1;
//		Comparator<Task> comparator = (Task task1, Task task2) -> 
//			Long.compare(task1.getTaskId(), task2.getTaskId()) * factor;
//			
//		return sortUsers(comparator);
//	}

//	private List<User> sortUsers(Comparator<Task> comparator) {
//		
//		List<User> userList = repository.
//		taskList.sort(comparator);
//		
//		return taskList;
//	}
	
}
