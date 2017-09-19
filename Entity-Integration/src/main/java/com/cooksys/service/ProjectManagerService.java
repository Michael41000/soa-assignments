package com.cooksys.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cooksys.dto.ProjectDto;
import com.cooksys.dto.ProjectManagerDto;
import com.cooksys.dto.ProjectManagerDtoOverdue;
import com.cooksys.entity.Project;
import com.cooksys.entity.ProjectManager;
import com.cooksys.exception.ReferencedEntityNotFoundException;
import com.cooksys.mapper.ProjectManagerMapper;
import com.cooksys.mapper.ProjectMapper;
import com.cooksys.repository.ProjectManagerRepository;
import com.cooksys.repository.ProjectRepository;

@Service
public class ProjectManagerService {

	private ProjectManagerRepository repo;
	private ProjectManagerMapper mapper;
	private ProjectRepository projectRepository;
	private ProjectMapper projectMapper;

	public ProjectManagerService(ProjectManagerRepository repo, ProjectManagerMapper mapper, ProjectRepository projectRepository, ProjectMapper projectMapper) {
		this.repo = repo;
		this.mapper = mapper;
		this.projectRepository = projectRepository;
		this.projectMapper = projectMapper;
	}
	
	public List<ProjectManagerDto> getAll() {
		return repo.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
	}

	public boolean has(Long id) {
		return repo.exists(id);
	}

	public ProjectManagerDto get(Long id) {
		mustExist(id);
		return mapper.toDto(repo.findOne(id));
	}

	public Long post(ProjectManagerDto projectManagerDto) {
		projectManagerDto.setId(null);
		return repo.save(mapper.toEntity(projectManagerDto)).getId();
	}

	public void put(Long id, ProjectManagerDto projectManagerDto) {
		mustExist(id);
		projectManagerDto.setId(id);
		repo.save(mapper.toEntity(projectManagerDto));
	}
	
	private void mustExist(Long id) {
		if(!has(id))
			throw new ReferencedEntityNotFoundException(ProjectManager.class, id);
	}

	public void delete(Long id) {
		mustExist(id);
		repo.delete(id);
	}

	
	public ProjectDto addProject(Long id, Long projectId) {
		Project project = projectRepository.findOne(projectId);
		project.setManager(repo.findOne(id));
		projectRepository.save(project);
		return projectMapper.toDto(project);
	}

	public Set<ProjectDto> getProjects(Long id) {
		return projectMapper.toDtos(repo.findOne(Long.valueOf(id)).getProjects());
	}

	public List<ProjectManagerDtoOverdue> getOverdueProjects() {
		List<ProjectManager> projectManagers = repo.findAll();
		List<ProjectManagerDtoOverdue> overdue = new ArrayList<ProjectManagerDtoOverdue>();
		for (ProjectManager projectManager : projectManagers)
		{
			int numLateProjects = 0;
			for (Project project : projectManager.getProjects())
			{
				if (project.getDueDate().before(new Date(Calendar.getInstance().getTime().getTime())))
				{
					numLateProjects++;
				}
			}
			if (numLateProjects > 0)
			{
				ProjectManagerDtoOverdue pmdo = mapper.toDtoOverdue(projectManager);
				pmdo.setOverdueProjects(numLateProjects);
				overdue.add(pmdo);
			}
		}
		
		Collections.sort(overdue, new Comparator<ProjectManagerDtoOverdue>() {

			@Override
			public int compare(ProjectManagerDtoOverdue o1, ProjectManagerDtoOverdue o2) {
				return o2.getOverdueProjects() - o1.getOverdueProjects();
			}
		});
		
		return overdue;
	}
}
