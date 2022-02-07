package com.lessayer.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.lessayer.entity.Task;

@Repository
@Transactional
public class TaskRepository {
	
	@PersistenceContext
	EntityManager entityManager;
	
	private TypedQuery<Task> query;
	private TypedQuery<Long> countQuery;
	
	// PSQL Name: "find_all_tasks"
	// PSQL Query: "select t from Task t"
	public List<Task> findAllTask() {
		
		query = entityManager.createNamedQuery("find_all_tasks", Task.class);
		return query.getResultList();
		
	}
	
	public Task findByTaskId(long taskId) {
		
		return entityManager.find(Task.class, taskId);
		
	}
	
	// PSQL Name: "find_all_tasks_by_userID",
	// PSQL Query: "select t from Task t where t.userId like :userId"
	public List<Task> findByUserId(long userId) {
		
		query = entityManager
				.createNamedQuery("find_all_tasks_by_userID", Task.class)
				.setParameter("userId", userId);
		return query.getResultList();
		
	}
	
	public Task createTask(Task task) {
		
		return entityManager.merge(task);
		
	}
	
	public void deleteTask(long taskId) {
		
		Task deletedTask = findByTaskId(taskId);
		entityManager.remove(deletedTask);
		
	}
	
	public Task updateTask(Task task) {
		
		return entityManager.merge(task);
		
	}
	
	// PSQL Name: "return_number_of_tasks",
	// PSQL Query: "select count(t) from Task t"
	public Long returnTaskCount() {
		
		countQuery = entityManager.createNamedQuery("return_number_of_tasks",
						Long.class);
		return countQuery.getSingleResult();
		
	}
}