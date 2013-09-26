package com.codetutr.service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.codetutr.domain.Person;

@Service
public class PersonServiceImpl implements PersonService {

	private static String[] names = {"Nikolaus Otto", "Robert Ford", "Gottlieb Daimler", "Lt. General Masaharu Homma"};
	private static Map<Long, Person> idPersionMap = new ConcurrentHashMap<>();
	private static AtomicLong nextId = new AtomicLong(-1);

	static {
		for(String name : names) {
			Long id = nextId.addAndGet(1);
			idPersionMap.put(id, new Person(name, 50));
		}
	}

	@Override
	public Person getRandom() {
		Person person = new Person();
		person.setName(randomName());
		person.setAge(randomAge());
		return person;
	}

	@Override
	public Person getById(Long id) {
		if (idPersionMap.containsKey(id))
			return idPersionMap.get(id);
		return Person.UNKNOWN;
	}
	
	@Override
	public void save(Person person) {
		Long id = nextId.addAndGet(1);
		idPersionMap.put(id, person);
	}

	@Override
	public Person removeById(Long id) {
		if (idPersionMap.containsKey(id)) {
			Person removed = idPersionMap.get(id);
			idPersionMap.remove(id);
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

}
