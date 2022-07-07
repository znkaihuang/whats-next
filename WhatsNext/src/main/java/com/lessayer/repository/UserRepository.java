package com.lessayer.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.lessayer.entity.Role;
import com.lessayer.entity.User;

@Repository
@Transactional
public class UserRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private TypedQuery<User> query;
	
	// PSQL Name = "find_all_users",
	// PSQL Query = "select u from User"
	public Optional<List<User>> findAllUsers() {
		
		query = entityManager.createNamedQuery("find_all_users", User.class);
		
		return Optional.ofNullable(query.getResultList());
	}
	
	public User findByUserId(Long userId) {
		
		return entityManager.find(User.class, userId);
		
	}
	
	// PSQL Name: "find_user_by_username",
	// PSQL Query: "select u from User u where u.userName like :username"
	public Optional<User> findByUserName(String userName) {
		
		query = entityManager.createNamedQuery("find_user_by_username", User.class)
							.setParameter("username", userName);
		
		User user = null;
		try {
			
			user = query.getSingleResult();
		
		}
		catch (NoResultException e) {
			// TODO: handle exception
		}

		return Optional.ofNullable(user);
			
	}
	
	// PSQL Name: "find_user_by_role",
	// PSQL Query: "select u from User u where u.role like :role"
	public List<User> findByRole(Role role) {
		
		query = entityManager.createNamedQuery("find_user_by_role", User.class)
							.setParameter("role", role);

		return query.getResultList();
			
	}
	
	public User createUser(User user) {
	
		return entityManager.merge(user);
		
	}
	
	public void deleteUser(Long userId) {
		
		entityManager.remove(findByUserId(userId));
		
	}
	
	public User updateUser(User user) {
		
		return entityManager.merge(user);
		
	}
	
}
