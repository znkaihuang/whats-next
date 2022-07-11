package com.lessayer.service;

import java.util.Properties;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
public class MailServiceConfiguration {

	private String host;
	private Integer port;
	private String userName;
	private String password;
	
	public MailServiceConfiguration() {
		
	}
	
	public MailServiceConfiguration(String host, Integer Port, String userName, String password) {
		
		this.host = host;
		this.port = Port;
		this.userName = userName;
		this.password = password;
		
	}

	public String getHost() {

		return host;
	}

	public void setHost(String host) {

		this.host = host;
	}

	public Integer getPort() {

		return port;
	}

	public void setPort(Integer port) {

		this.port = port;
	}

	public String getUserName() {

		return userName;
	}

	public void setUserName(String userName) {

		this.userName = userName;
	}

	public String getPassword() {

		return password;
	}

	public void setPassword(String password) {

		this.password = password;
	}

	public JavaMailSender getMailSender() {

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(host);
		mailSender.setPort(port);

		mailSender.setUsername(userName);
		mailSender.setPassword(password);

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		return mailSender;

	}

}
