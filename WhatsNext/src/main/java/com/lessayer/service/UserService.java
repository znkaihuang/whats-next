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
	
	public Optional<User> retrieveUser(String userName) {
		
		return repository.findByUserName(userName);
		
	}
	
	public List<User> retrieveAllAdmins() {
		
		return repository.findByRole(Role.ADMIN);
		
	}
	
	public List<User> retrieveAllUsers() {
		
		return repository.findByRole(Role.USER);
		
	}
	
	public boolean isUserExist(String userName) {
		
		return (retrieveUser(userName).isPresent()) ? true : false;
		
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
	
}
