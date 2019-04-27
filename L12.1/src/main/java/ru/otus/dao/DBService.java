package ru.otus.dao;

import ru.otus.datasets.DataSet;
import ru.otus.datasets.UserDataSet;

import java.util.List;

/**
 * @author v.chibrikov
 */
public interface DBService {
	String getLocalStatus();

	<T extends DataSet> void save(T dataSet);

	UserDataSet read(long id);

	UserDataSet readByName(String name);

	List<UserDataSet> readAll();

	void shutdown();
}
