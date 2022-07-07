package com.lessayer.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;


@Entity
@NamedQuery(
		name = "find_all_users",
		query = "select u from User u"
)
@NamedQuery(
		name = "find_user_by_username",
		query = "select u from User u where u.userName like :username"
)
@NamedQuery(
		name = "find_user_by_role",
		query = "select u from User u where u.role like :role"
)
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	private String userName;
	private String password;
	private String email;
	private Role role;
	private Date registerDate;
	private Date lastLoginDate;
	
	public User() {
		
	}
	
	public User(String userName, String password, String email, 
			Role role, Date registerDate, Date lastLoginDate) {
		
		super();
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.role = role;
		this.registerDate = registerDate;
		this.lastLoginDate = lastLoginDate;
	
	}

	public Long getUserId() {
		
		return userId;
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
	
	public String getEmail() {
		
		return email;
	}
	
	public void setEmail(String email) {
	
		this.email = email;
	}
	
	public Role getRole() {
	
		return role;
	}
	
	public void setRole(Role role) {
	
		this.role = role;
	}
	
	public Date getLastLoginDate() {
	
		return lastLoginDate;
	}
	
	public void setLastLoginDate(Date lastLoginDate) {
	
		this.lastLoginDate = lastLoginDate;
	}
	
	public Date getRegisterDate() {
	
		return registerDate;
	}

	@Override
	public String toString() {
		return String.format("UserId=%d\n"
				+ "  userName=%s\n"
				+ "  password=%s\n"
				+ "  email=%s\n"
				+ "  role=%s\n"
				+ "  registerDate=%s\n"
				+ "  lastLoginDate=%s\n",
				userId, userName, password, email, role, registerDate, lastLoginDate);
	}
	
}
