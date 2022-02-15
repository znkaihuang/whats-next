package com.lessayer.service;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailService {
	
	private MailServiceConfiguration configuration;
	
    private JavaMailSender emailSender;
    
    public void config(MailServiceConfiguration configuration) {
    	
    	this.configuration = configuration;
    	this.emailSender = configuration.getMailSender();
    	
    }
    
    public void sendSimpleMessage(String to, String subject, String text) {

    	        SimpleMailMessage message = new SimpleMailMessage(); 
    	        message.setFrom(this.configuration.getUserName());
    	        message.setTo(to); 
    	        message.setSubject(subject); 
    	        message.setText(text);
    	        emailSender.send(message);

   }
    
}
