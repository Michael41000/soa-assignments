package com.example.Friendlr.controller;

import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Friendlr.dto.PersonDto;
import com.example.Friendlr.service.PersonService;

@RestController
@RequestMapping("person")
public class PersonController {
	
	private PersonService personService;
	
	public PersonController(PersonService personService)
	{
		this.personService = personService;
	}
	
	@GetMapping
	public Set<PersonDto> getPersons()
	{
		return personService.getPersons();
	}
	
	@GetMapping("{id}")
	public PersonDto getPerson(@PathVariable Long id, HttpServletResponse response)
	{
		PersonDto personDto = personService.getPerson(id);
		
		if (personDto == null)
		{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		return personDto;
	}
	
	@PostMapping
	public PersonDto createPerson(@RequestBody PersonDto personDto, HttpServletResponse response)
	{
		PersonDto createdPersonDto = personService.createPerson(personDto);
		if (createdPersonDto == null)
		{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		else
		{
			response.setStatus(HttpServletResponse.SC_CREATED);
		}
		return createdPersonDto;
	}
	
	@PutMapping("{id}")
	public PersonDto editPerson(@PathVariable Long id, @RequestBody PersonDto personDto, HttpServletResponse response)
	{
		PersonDto editedPersonDto = personService.editPerson(id, personDto);
		if (editedPersonDto == null)
		{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		return editedPersonDto;
	}
	
	@DeleteMapping("{id}")
	public PersonDto deletePerson(@PathVariable Long id, HttpServletResponse response)
	{
		PersonDto deletedPersonDto = personService.deletePerson(id);
		if (deletedPersonDto == null)
		{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		return deletedPersonDto;
	}
	
	@GetMapping("{id}/friends")
	public Set<PersonDto> getFriends(@PathVariable Long id, HttpServletResponse response)
	{
		Set<PersonDto> friends = personService.getFriends(id);
		
		if (friends == null)
		{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		return friends;
	}
	
	@PostMapping("{id}/friends/{friendId}")
	public PersonDto addFriend(@PathVariable Long id, @PathVariable Long friendId, HttpServletResponse response)
	{
		PersonDto friendDto = personService.addFriend(id, friendId);
		
		if (friendDto == null)
		{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		return friendDto;
	}
	
	@DeleteMapping("{id}/friends/{friendId}")
	public PersonDto deleteFriend(@PathVariable Long id, @PathVariable Long friendId, HttpServletResponse response)
	{
		PersonDto friendDto = personService.deleteFriend(id, friendId);
		
		if (friendDto == null)
		{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		return friendDto;
	}

}
