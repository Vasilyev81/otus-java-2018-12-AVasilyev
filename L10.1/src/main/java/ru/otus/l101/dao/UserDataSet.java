package ru.otus.l101.dao;

import ru.otus.l101.dao.DataSet;

public class UserDataSet extends DataSet {
	private final String name;
	private int age;

	public UserDataSet(long id, String name, int age) {
		super(id);
		this.name = name;
		this.age = age;
	}

	public UserDataSet(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String toString() {
		return "UserDataSet id:" + getId() + ", name: " + getName() + ", age: " + getAge() + ".";
	}
}
