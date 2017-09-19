package com.cooksys.dto;

import java.util.Set;

import com.cooksys.dto.datatype.Reference;
import com.cooksys.entity.Project;

public class ProjectManagerDtoOverdue {
	
	private Long id;
	
	private String firstName;
	
	private String lastName;
	
	private Set<Reference<Project, Long>> projects;
	
	private Integer overdueProjects;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Set<Reference<Project, Long>> getProjects() {
		return projects;
	}

	public void setProjects(Set<Reference<Project, Long>> projects) {
		this.projects = projects;
	}

	public Integer getOverdueProjects() {
		return overdueProjects;
	}

	public void setOverdueProjects(Integer overdueProjects) {
		this.overdueProjects = overdueProjects;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjectManagerDtoOverdue other = (ProjectManagerDtoOverdue) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
