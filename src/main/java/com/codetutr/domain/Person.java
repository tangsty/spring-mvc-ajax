package com.codetutr.domain;


public class Person {
	public static final Person UNKNOWN = new Person(-1L, "N/A", -1);

	private Long id;
	private String name;
	private Integer age;

	public Person() {
		super();
		id = -1L;
		name = null;
		age = null;
	}

	public Person(Long id, String name, Integer age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		return "Person [id=" + id + " name=" + name + ", age=" + age + "]";
	}


}
