package ru.otus.servlets;

import ru.otus.datasets.*;
import ru.otus.messageSystem.Addressee;

import java.util.List;

public interface FrontendService extends Addressee {
    void init();

	void readAll();

	List<UserDataSet> getResultList();

	void setResultList(List<UserDataSet> userDataSetList);

	<T extends DataSet> void save(T... companyDataSet);

	void findUserByID(long id);

	void returnUserResult(UserDataSet dataSet);
}

