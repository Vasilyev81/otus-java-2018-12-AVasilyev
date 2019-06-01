package ru.otus.dao;

import org.hibernate.ObjectNotFoundException;
import ru.otus.datasets.DataSet;
import ru.otus.datasets.UserDataSet;

import java.util.List;

/**
 * @author v.chibrikov
 */
public interface DBService {
	String getLocalStatus();

	<T extends DataSet> void save(T dataSet);

	UserDataSet read (long id) throws ObjectNotFoundException;

	UserDataSet readByName(String name);

	List<UserDataSet> readAll();

	void shutdown();
}
