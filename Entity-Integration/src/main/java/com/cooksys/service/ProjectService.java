package com.cooksys.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.cooksys.dto.ProjectDto;
import com.cooksys.mapper.ProjectMapper;
import com.cooksys.repository.ProjectRepository;

@Service
public class ProjectService {

	private ProjectRepository projectRepository;
	private ProjectMapper projectMapper;
	
	public ProjectService(ProjectRepository projectRepository, ProjectMapper projectMapper)
	{
		this.projectRepository = projectRepository;
		this.projectMapper = projectMapper;
	}
	
	public Set<ProjectDto> getAll() 
	{
		return projectMapper.toDtos(projectMapper.toSet(projectRepository.findAll()));
	}

	public ProjectDto create(ProjectDto projectDto) {
		projectDto.setId(null);
		return projectMapper.toDto(projectRepository.save(projectMapper.fromDto(projectDto)));
	}

	public Set<ProjectDto> getOverdue() {
		return projectMapper.toDtos(projectRepository.findOverdue());
	}

}
