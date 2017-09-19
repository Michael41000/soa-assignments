package com.cooksys.mapper;

import org.mapstruct.Mapper;

import com.cooksys.dto.ProjectManagerDto;
import com.cooksys.dto.ProjectManagerDtoOverdue;
import com.cooksys.entity.ProjectManager;

@Mapper(componentModel = "spring", uses = { ReferenceMapper.class })
public interface ProjectManagerMapper {

	ProjectManagerDto toDto(ProjectManager entity);

	ProjectManager toEntity(ProjectManagerDto dto);

	ProjectManagerDtoOverdue toDtoOverdue(ProjectManager entity);
}
