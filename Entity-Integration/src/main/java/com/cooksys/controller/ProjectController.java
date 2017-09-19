package com.cooksys.controller;

import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.dto.ProjectDto;
import com.cooksys.service.ProjectService;

@RestController
@RequestMapping("project")
public class ProjectController {
	
	private ProjectService projectService;
	
	public ProjectController(ProjectService projectService)
	{
		this.projectService = projectService;
	}
	
	@GetMapping
	public Set<ProjectDto> getAll()
	{
		return projectService.getAll();
	}
	
	@PostMapping
	public ProjectDto create(@RequestBody ProjectDto projectDto)
	{
		return projectService.create(projectDto);
	}
	
	@GetMapping("overdue")
	public Set<ProjectDto> getOverdue()
	{
		return projectService.getOverdue();
	}

}
