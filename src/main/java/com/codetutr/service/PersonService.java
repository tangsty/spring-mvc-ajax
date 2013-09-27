package com.codetutr.service;

import java.util.List;

import com.codetutr.domain.Person;

public interface PersonService {
	public Person getRandom();
	public Person getById(Long id);
	public void save(Person person);
	public Person update(Person person);
	public Person removeById(Long id);
	public List<Person> getPeople();
}
