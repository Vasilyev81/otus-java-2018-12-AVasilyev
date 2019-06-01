package ru.otus.dao;

import ru.otus.datasets.*;

import java.util.List;

public interface DBService {
	String getLocalStatus();

	<T extends DataSet> void save(T dataSet);

	UserDataSet read(long id);

	UserDataSet readByName(String name);

	List<UserDataSet> readAll();

	void shutdown();
}
