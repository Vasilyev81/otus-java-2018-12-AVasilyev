package ru.otus.l111.dbservice;

import ru.otus.l111.datasets.DataSet;

import java.util.List;

public interface DBService<T extends DataSet> {
	String getLocalStatus();

	void save(T... dataSet);

	T read(long id);

	void shutdown();

	List<T> readAll();

	T readByName(String name);
}
