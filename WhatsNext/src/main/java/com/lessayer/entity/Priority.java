package com.lessayer.entity;

public enum Priority {
	
	LOW("LOW"), MEDIUM("MEDIUM"), HIGH("HIGH"), CRITICAL("CRITICAL");
	
	private final String string;
	
	Priority(String string) {
		this.string = string;
	}
	
	public String getString() {
		return this.string;
	}
	
}
