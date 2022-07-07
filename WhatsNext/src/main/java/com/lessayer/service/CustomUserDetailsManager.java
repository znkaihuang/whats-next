package com.lessayer.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;

public class CustomUserDetailsManager implements UserDetailsManager {
	
	private UserService service;
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final Map<String, UserDetails> users = new HashMap<>();
	
	public CustomUserDetailsManager(UserService service) {
		
		this.service = service;
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		List<com.lessayer.entity.User> adminList = service.retrieveAllAdmins();
		List<com.lessayer.entity.User> userList = service.retrieveAllNormalUsers();
		
		for(com.lessayer.entity.User admin : adminList) {
			
			createUser(User.builder()
							.passwordEncoder(encoder::encode)
							.username(admin.getUserName())
							.password(admin.getPassword())
							.roles("ADMIN")
							.build());
			// logger.info("user {}\n password {}", admin.getUserName(), admin.getPassword());
		
		}
		
		for(com.lessayer.entity.User user : userList) {
			
			createUser(User.builder()
							.passwordEncoder(encoder::encode)
							.username(user.getUserName())
							.password(user.getPassword())
							.roles("USER")
							.build());
			// logger.info("user {}\n password {}", user.getUserName(), user.getPassword());
			
		}
		
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserDetails user = users.get(username);
		if (user == null) {
			
			throw new UsernameNotFoundException(username);
			
		}
		return new User(user.getUsername(), user.getPassword(), user.isEnabled(),
				user.isAccountNonExpired(), user.isCredentialsNonExpired(), 
				user.isAccountNonLocked(), user.getAuthorities());
		
	}

	@Override
	public void createUser(UserDetails user) {
		
		users.put(user.getUsername(), user);
		
	}

	@Override
	public void updateUser(UserDetails user) {
		
		users.put(user.getUsername(), user);
		
	}

	@Override
	public void deleteUser(String userName) {
		
		users.remove(userName);
		
	}

	@Override
	public void changePassword(String oldPassword, String newPassword) {
	
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String userName = currentUser.getName();
		UserDetails user = users.get(userName);
		UserDetails newUser = new User(user.getUsername(), newPassword, 
				user.isEnabled(), true, true, true, user.getAuthorities());
		deleteUser(userName);
		createUser(newUser);
		
	}

	@Override
	public boolean userExists(String username) {
		
		return users.containsKey(username);
	}

	public UserService getService() {
		return service;
	}
	
	
}
