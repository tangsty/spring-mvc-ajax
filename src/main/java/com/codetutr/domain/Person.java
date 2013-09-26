package com.codetutr.domain;


public class Person {

	private String name;
	private Integer age;

	public static final Person UNKNOWN = new Person("N/A", -1);

	public Person() {
		super();
		name = null;
		age = null;
	}

	public Person(String name, Integer age) {
		this.name = name;
		this.age = age;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + "]";
	}
	
	
}
