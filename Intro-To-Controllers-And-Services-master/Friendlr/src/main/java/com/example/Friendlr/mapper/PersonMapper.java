package com.example.Friendlr.mapper;

import java.util.Set;

import org.mapstruct.Mapper;

import com.example.Friendlr.dto.PersonDto;
import com.example.Friendlr.entity.Person;

@Mapper(componentModel="spring")
public interface PersonMapper {

	PersonDto toPersonDto(Person person);
	
	Person toPerson(PersonDto personDto);
	
	Set<PersonDto> toPersonDtoSet(Set<Person> persons);
	
	Set<Person> toPersonSet(Set<PersonDto> personDtos);
}
