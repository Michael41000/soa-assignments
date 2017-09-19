package com.cooksys.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cooksys.entity.Project;


public interface ProjectRepository extends JpaRepository<Project, Long>{

	@Query("SELECT p FROM Project p WHERE p.dueDate < CURRENT_TIMESTAMP")
	Set<Project> findOverdue();
}
