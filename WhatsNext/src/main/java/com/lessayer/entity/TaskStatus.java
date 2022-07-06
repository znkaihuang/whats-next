package com.lessayer.entity;

public enum TaskStatus {
	NEW("NEW"), ON_GOING("ON GOING"), PENDING("PENDING"), FINISHED("FINISHED");
	
	private final String string;
	
	TaskStatus(String string) {
		this.string = string;
	}

	public String getString() {
		return this.string;
	}
	
}
