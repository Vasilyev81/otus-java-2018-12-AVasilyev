package ru.otus.l101.executor;

import ru.otus.l101.dao.DataSet;
import ru.otus.l101.executor.reflection.ReflectionHelper;

import java.sql.*;
import java.util.*;

public class DbExecutorImpl implements DbExecutor {
	private final Connection conn;
	private DbExecutorHelper executorHelper;

	public DbExecutorImpl(Connection conn) {
		this.conn = conn;
		this.executorHelper = new DbExecutorHelper();
	}

	@Override
	public <T extends DataSet> void analyzeClass(Class<T> clazz) {
		executorHelper.analyzeClass(clazz);
	}

	@Override
	public <T extends DataSet> void save(T user) throws SQLException {
		String preparedSql = executorHelper.getSqlToSaveClass(user.getClass());
		PreparedStatement preparedStatement = conn.prepareStatement(preparedSql);
		List<String> fieldsNames = executorHelper.getClassStructure(user.getClass());
		int index = 1;
		for (String fieldName : fieldsNames) {
			Object value = ReflectionHelper.getFieldValue(user, fieldName);
			preparedStatement.setObject(index, value);
			index++;
		}
		preparedStatement.executeUpdate();
		preparedStatement.close();
		String sqlLastCreated = "SELECT * FROM users ORDER BY id DESC LIMIT 1;";
		preparedStatement = conn.prepareStatement(sqlLastCreated);// Getting max saved id, i.e. last saved id.
		ResultSet resultSet = preparedStatement.executeQuery();
		resultSet.next();
		Object id = resultSet.getObject("id");
		resultSet.close();
		preparedStatement.close();
		ReflectionHelper.setFieldValue(user, "id", id);
		System.out.println("Save to DB & save created 'id' to object: " + user);
	}

	@Override
	public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
		String sqlString = executorHelper.getSqlToLoadClass(clazz);
		List<String> fieldsNames = ReflectionHelper.getAllFieldsNames(clazz);
		PreparedStatement preparedStatement = conn.prepareStatement(sqlString);
		preparedStatement.setObject(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		resultSet.next();
		List<Object> values = new ArrayList<>();
		for (String name : fieldsNames) {
			values.add(resultSet.getObject(name));
		}
		resultSet.close();
		preparedStatement.close();
		return ReflectionHelper.instantiateByFieldsNames(clazz, fieldsNames, values);
	}
}
