package com.lessayer.service;


import java.sql.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lessayer.entity.Role;
import com.lessayer.entity.User;

@SpringBootTest
class UserServiceTests {
	
	@Autowired
	private UserService service;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Test
	void testRetrieveUser() {
		
		String userName = "Guest";
		Optional<User> user = service.retrieveUserByName(userName);
		if(user.isEmpty()) {
			
			logger.info("No such user {}", userName);
		
		}
		else {
		
			logger.info("User info {}", user.get());
			
		}
	}
	
	@Test
	void testRetrieveAllAdmins() {
		
		logger.info("List all the administers:\n{}", service.retrieveAllAdmins());
		
	}
	
	@Test
	void testIsUserExist() {
		
		String userName = "Guest";
		if(service.isUserExist(userName)) {
			
			logger.info("User {} has already existed", userName);
			
		}
		else {	
			
			logger.info("User {} does not exist", userName);
			
		}
		
	}

	@Test
	void testCreateUser() {
		
		String userName = "Terry";
		String password = "$$12345";
		String email = "terry@lessayer.com";
		service.createUser(userName, password, email, Role.USER);
		logger.info("Create user {}", userName);
		
	}
	
	@Test
	void testCreateExistUser() {
		
		String userName = "Kevin";
		String password = "!Ohmygodness8269";
		String email = "test@test.com";
		
		try {
		
			service.createUser(userName, password, email, Role.ADMIN);
		
		}
		catch(RuntimeException e) {
			
			logger.info("The user {} has already existed.", userName);
			
		}
	}
	
	@Test
	void testDeleteUser() {
		
		String userName = "Terry";
		User user = service.retrieveUserByName(userName).get();
		service.deleteUser(user.getUserId());
		logger.info("Delete user {}", userName);
		
	}

	@Test
	void testUpdatePassword() {
		
		String userName = "Mien";
		String password = "mien@1116";
		logger.info("Before update password {}", service.retrieveUserByName(userName)
				.get().getPassword());
		service.updatePassword(userName, password);
		logger.info("After update password {}", service.retrieveUserByName(userName)
				.get().getPassword());
		
	}

	@Test
	void testUpdateLastLoginDate() {
		
		String userName = "Mien";
		logger.info("Before update last login date {}", service.retrieveUserByName(userName)
				.get().getLastLoginDate());
		service.updateLastLoginDate(userName, Date.valueOf("2022-04-11"));
		logger.info("After update last login date {}", service.retrieveUserByName(userName)
				.get().getLastLoginDate());
		
	}
	
	@Test
	void testUpdateEmail() {
		
		String userName = "Tam";
		logger.info("Before update email {}", service.retrieveUserByName(userName)
				.get().getEmail());
		service.updateEmail(userName, "guest@test.com");
		logger.info("After update last login date {}", service.retrieveUserByName(userName)
				.get().getEmail());
		
	}
	
}
