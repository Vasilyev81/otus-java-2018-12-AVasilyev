package ru.otus.dao;

import org.hibernate.ObjectNotFoundException;
import ru.otus.datasets.*;
import ru.otus.messageSystem.Addressee;

import java.util.List;

public interface DBService extends Addressee {
	void init();

	String getLocalStatus();

	<T extends DataSet> void save(List<T> dataSet);

	<T extends DataSet> void save(T dataSet);

	UserDataSet read(long id) throws ObjectNotFoundException;

	UserDataSet readByName(String name);

	List<UserDataSet> readAll();

	void shutdown();
}
