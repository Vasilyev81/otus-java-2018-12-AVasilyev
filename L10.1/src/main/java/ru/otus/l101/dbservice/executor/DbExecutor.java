package ru.otus.l101.dbservice.executor;

import ru.otus.l101.dao.DataSet;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public interface DbExecutor {
	<T extends DataSet> void analyzeClass(Class<T> clazz) throws SQLException;
	<T extends DataSet> void save(T user) throws SQLException;
	<T extends DataSet> T load(long id, Class<T> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, SQLException;
}
