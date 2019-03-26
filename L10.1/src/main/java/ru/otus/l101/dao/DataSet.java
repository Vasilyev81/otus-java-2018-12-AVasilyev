package ru.otus.l101.dao;

public abstract class DataSet {
	private long id;

	public DataSet(long id) {
		this.id = id;
	}

	public DataSet() {
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}
}
