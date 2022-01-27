package com.lessayer.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lessayer.entity.Task;

@Repository
@Transactional
public class TaskRepository {
	
	@PersistenceContext
	EntityManager entityManager;
	
	public List<Task> findAll() {
		
		TypedQuery<Task> query = entityManager.createNamedQuery("find_all_tasks", Task.class);
		return query.getResultList();
		
	}
	
	public Task findById(long id) {
		
		return entityManager.find(Task.class, id);
		
	}
	
	public Task insert(Task task) {
		
		return entityManager.merge(task);
		
	}
	
}