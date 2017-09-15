package com.example.Friendlr.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.Friendlr.dto.PersonDto;
import com.example.Friendlr.entity.Person;
import com.example.Friendlr.mapper.PersonMapper;

@Service
public class PersonService {

	private static Long index = (long) 0;
	private Set<Person> persons;
	private PersonMapper personMapper;
	
	public PersonService(PersonMapper personMapper)
	{
		persons = new HashSet<Person>();
		this.personMapper = personMapper;
	}
	
	public Set<PersonDto> getPersons()
	{
		return personMapper.toPersonDtoSet(persons);
	}
	
	public PersonDto getPerson(Long id)
	{
		for (Person person : persons)
		{
			if (person.getId().equals(id))
			{
				return personMapper.toPersonDto(person);
			}
		}
		return null;
	}
	
	public PersonDto createPerson(PersonDto personDto)
	{
		if (personDto == null)
		{
			return null;
		}
		personDto.setId(index++);
		persons.add(personMapper.toPerson(personDto));
		return personDto;
	}

	public PersonDto editPerson(Long id, PersonDto personDto) {
		if (personDto == null)
		{
			return null;
		}
		
		for (Person p : persons)
		{
			if (p.getId().equals(id))
			{
				personDto.setId(id);
				persons.remove(p);
				p.setFirstName(personDto.getFirstName());
				p.setLastName(personDto.getLastName());
				persons.add(p);
				return personDto;
			}
		}
		
		return null;
	}

	public PersonDto deletePerson(Long id) {
		for (Person person : persons)
		{
			if (person.getFriends().contains(getPersonEntity(id)))
			{
				person.getFriends().remove(getPersonEntity(id));
			}
		}
		
		for (Person person : persons)
		{
			if (person.getId().equals(id))
			{
				persons.remove(person);
				return personMapper.toPersonDto(person);
			}
		}
		return null;
	}

	private Person getPersonEntity(Long id)
	{
		for (Person person : persons)
		{
			if (person.getId().equals(id))
			{
				return person;
			}
		}
		return null;
	}
	
	public Set<PersonDto> getFriends(Long id) {
		PersonDto personDto = getPerson(id);
		
		if (personDto == null)
		{
			return null;
		}
		
		return personMapper.toPersonDtoSet(getPersonEntity(id).getFriends());
	}

	public PersonDto addFriend(Long id, Long friendId) {
		PersonDto personDto = getPerson(id);
		PersonDto friendDto = getPerson(friendId);
		
		if (personDto == null || friendDto == null)
		{
			return null;
		}
		
		getPersonEntity(id).getFriends().add(getPersonEntity(friendId));
		getPersonEntity(friendId).getFriends().add(getPersonEntity(id));
		return friendDto;
	}
	
	public PersonDto deleteFriend(Long id, Long friendId) {
		PersonDto personDto = getPerson(id);
		PersonDto friendDto = getPerson(friendId);
		
		if (personDto == null || friendDto == null)
		{
			return null;
		}
		
		Set<Person> personFriends = getPersonEntity(id).getFriends();
		Set<Person> friendFriends = getPersonEntity(friendId).getFriends();
		
		if (!personFriends.contains(getPersonEntity(friendId)) || !friendFriends.contains(getPersonEntity(id)))
		{
			return null;
		}
		
		getPersonEntity(id).getFriends().remove(getPersonEntity(friendId));
		getPersonEntity(friendId).getFriends().remove(getPersonEntity(id));
		
		return friendDto;
		
	}
}
