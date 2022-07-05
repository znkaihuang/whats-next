package com.lessayer.securing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.lessayer.service.CustomUserDetailsManager;
import com.lessayer.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserService service;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
				.antMatchers("/login", "/logout", "/webjars/**")
				.permitAll()
				.antMatchers("/**")
				.authenticated()
				.and()
			.formLogin(form -> form
				.loginPage("/login")
				.failureUrl("/login?error")
				.permitAll()
					)
			.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout")
				.permitAll();
		http.formLogin();
		http.httpBasic();
	
	}
	
	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		
		return new CustomUserDetailsManager(service);
		
	}
	
}
