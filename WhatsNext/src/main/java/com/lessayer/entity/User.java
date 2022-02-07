package com.lessayer.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

public class User {
	
	private Long userId;
	
	public User(Long userId) {
		
		this.userId = userId;
		
	}
	
	public Long getUserId() {
		
		return userId;
	}
	
}
