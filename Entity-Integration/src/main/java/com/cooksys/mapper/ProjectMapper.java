package com.cooksys.mapper;

import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;

import com.cooksys.dto.ProjectDto;
import com.cooksys.entity.Project;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
	
	ProjectDto toDto(Project project);
	
	Project fromDto(ProjectDto projectDto);
	
	Set<ProjectDto> toDtos(Set<Project> list);
	
	Set<Project> toSet(List<Project> list);
	
	Set<Project> fromDtos(Set<ProjectDto> projectDtos);

}
