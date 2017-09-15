package com.example.Friendlr.entity;

import java.util.HashSet;
import java.util.Set;

public class Person {
	
	private Long id;
	private String firstName;
	private String lastName;
	private Set<Person> friends = new HashSet<Person>();
	
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
	public Set<Person> getFriends() {
		return friends;
	}
	public void setFriends(Set<Person> friends) {
		this.friends = friends;
	}
	
	
	
	

}
