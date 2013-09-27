package com.codetutr.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.codetutr.domain.Person;

@Service
public class PersonServiceImpl implements PersonService {

	private static String[] names = {"Nikolaus Otto", "Robert Ford", "Gottlieb Daimler", "Lt. General Masaharu Homma"};
	private static Map<Long, Person> idPersonMap = new ConcurrentHashMap<>();
	private static AtomicLong nextId = new AtomicLong(-1);

	static {
		for(String name : names) {
			Long id = nextId.addAndGet(1);
			idPersonMap.put(id, new Person(id, name, 50));
		}
	}

	@Override
	public Person getRandom() {
		Person person = new Person();
		person.setName(randomName());
		person.setAge(randomAge());
		save(person);
		return person;
	}

	@Override
	public Person getById(Long id) {
		if (idPersonMap.containsKey(id))
			return idPersonMap.get(id);
		return Person.UNKNOWN;
	}
	
	@Override
	public void save(Person person) {
		Long id = nextId.addAndGet(1);
		person.setId(id);
		idPersonMap.put(id, person);
	}

	@Override
	public Person update(Person person) {
		Long id = person.getId();
		if (idPersonMap.containsKey(id)) {
			idPersonMap.put(id, person);
		}
		else {
			person = Person.UNKNOWN;
		}
		return person;
	}

	@Override
	public Person removeById(Long id) {
		if (idPersonMap.containsKey(id)) {
			Person removed = idPersonMap.get(id);
			idPersonMap.remove(id);
			return removed;
		}
		return Person.UNKNOWN;
	}
	
	private Integer randomAge() {
		Random random = new Random();
		return 10 + random.nextInt(100);
	}

	private String randomName() {
		Random random = new Random();
		return names[random.nextInt(names.length)];
	}

	@Override
	public List<Person> getPeople() {
		int idCnt = idPersonMap.size();
		Long[] ids = new Long[idCnt];
		idPersonMap.keySet().toArray(ids);
		Arrays.sort(ids);
		List<Person> results = new ArrayList<>();
		for(Long id : ids) {
			results.add(idPersonMap.get(id));
		}
		return results;
	}
}
