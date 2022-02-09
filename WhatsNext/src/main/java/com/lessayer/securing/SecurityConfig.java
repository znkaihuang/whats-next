package com.lessayer.securing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
				.anyRequest()
				.authenticated()
				.and()
			.logout()
				.permitAll();
		http.formLogin();
		http.httpBasic();
	
	}
	
	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		UserDetails user1 =
			 User.withDefaultPasswordEncoder()
				.username("kevin")
				.password("ohmygod123")
				.roles("ADMIN")
				.build();
		UserDetails user2 =
				 User.withDefaultPasswordEncoder()
					.username("mien")
					.password("test")
					.roles("USER")
					.build();
			
		return new InMemoryUserDetailsManager(user1, user2);
	}
	
}
