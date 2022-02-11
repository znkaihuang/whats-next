package com.lessayer.entity;


import java.sql.Date;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lessayer.repository.UserRepository;

@SpringBootTest
class UserRepositoryTests {
	
	@Autowired
	private UserRepository repository;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Test
	void testFindByUserId() {
		
		Long userId = 1L;
		logger.info("User info {}", repository.findByUserId(userId));
	
	}
	
	@Test
	void testfindByUserName() {
		
		String userName = "Kevin";
		logger.info("User info {}", repository.findByUserName(userName));
		
	}
	
	@Test
	void testfindByRole() {
		
		Role role = Role.ADMIN;
		logger.info("User info {}", repository.findByRole(role));
		
	}
	
	@Test
	void testCreateAndDeleteUser() {
		
		User user = new User("Guest", "guest", "", Role.GUEST, 
				Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()));		
		repository.createUser(user);
		logger.info("Create user {}", user);

		String userName = "Guest";
		repository.deleteUser(repository.findByUserName(userName).get().getUserId());
		logger.info("Delete user {}", user);
		
	}

	@Test
	void testUpdateUser() {
		
		String userName = "Tam";
		User user = repository.findByUserName(userName).get();
		user.setPassword("guest");
		repository.updateUser(user);
		logger.info("Update user {}", user);
		
	}

}
