package com.lessayer.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.lessayer.controller")
public class WhatsNextApplication {

	public static void main(String[] args) {
		SpringApplication.run(WhatsNextApplication.class, args);
	}

}
