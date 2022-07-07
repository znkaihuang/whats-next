package com.lessayer.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Comparator;
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
	
	public final Integer USER_PER_PAGE = 8;
	private Integer totalPage;
	private Integer totalUserNum;
	
	public Optional<List<User>> retrieveAllUsers() {
		return repository.findAllUsers();
	}
	
	public Optional<List<User>> retrieveAllUsers(Boolean ascendingOrder) {
		List<User> userList = repository.findAllUsers().get();
		sortUsersByRole(userList, ascendingOrder);
		
		return Optional.ofNullable(userList);
	}
	
	public Optional<List<User>> retrieveAllUsersByPage(Integer pageNum) {
		
		Optional<List<User>> userListOptional = retrieveAllUsers();
		calculateTotalPageAndUsers(userListOptional);
		return retrieveAllUsersByPage(userListOptional, pageNum);
	}

	public Optional<List<User>> retrieveAllUsersByPage(Integer pageNum, Boolean ascendingOrder) {
		
		Optional<List<User>> userListOptional = retrieveAllUsers(ascendingOrder);
		calculateTotalPageAndUsers(userListOptional);
		return retrieveAllUsersByPage(userListOptional, pageNum);
	}
	
	private Optional<List<User>> retrieveAllUsersByPage(Optional<List<User>> userListOptional, Integer pageNum) {
		if (userListOptional.isEmpty()) {
			return userListOptional;
		}
		else {
			return Optional.ofNullable(userListOptional.get().subList((pageNum - 1) * USER_PER_PAGE, 
					Math.min(userListOptional.get().size(), pageNum * USER_PER_PAGE)));
		}
	}
	
	private void calculateTotalPageAndUsers(Optional<List<User>> userListOptional) {
		
		if (userListOptional.isEmpty()) {
			totalPage = totalUserNum = 0;
		}
		else {
			totalUserNum = userListOptional.get().size();
			if (totalUserNum == 0) {
				totalPage = 0;
			}
			else if (totalUserNum % USER_PER_PAGE == 0) {
				totalPage = totalUserNum / USER_PER_PAGE;
			}
			else {
				totalPage = (totalUserNum / USER_PER_PAGE) + 1;
			}
		}
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
	
	public void sortUsersByRole(List<User> userList, Boolean ascendingOrder) {
		
		Integer factor = (ascendingOrder) ? 1 : -1;
		Comparator<User> comparator = (User user1, User user2) -> 
			Long.compare(user1.getRole().ordinal(), user2.getRole().ordinal()) * factor;
		userList.sort(comparator);

	}
	
	public Integer getTotalPage() {
		return totalPage;
	}
	
	public Integer getTotalUserNum() {
		return totalUserNum;
	}
	
}
