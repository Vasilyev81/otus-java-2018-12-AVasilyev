package ru.otus.l101.executor;

import ru.otus.l101.dao.DataSet;
import ru.otus.l101.dao.TableName;
import ru.otus.l101.dbutils.DbHelper;
import ru.otus.l101.executor.reflection.ReflectionHelper;
import ru.otus.l101.executor.reflection.sqlcash.SqlStatementsCash;

import java.math.BigInteger;
import java.sql.*;
import java.util.*;

public class DbExecutorImpl implements DbExecutor {
	private final Connection conn;
	private SqlStatementsCash sqlCash;
	private DbHelper dbHelper;

	public DbExecutorImpl(Connection conn, DbHelper dbHelper) {
		this.conn = conn;
		this.dbHelper = dbHelper;
		this.sqlCash = new SqlStatementsCash(dbHelper.getDefaultTableName());
	}

	@Override
	public <T extends DataSet> void analyzeClass(Class<T> clazz) throws SQLException {
		sqlCash.analyzeClass(clazz);
	}

	@Override
	public <T extends DataSet> void save(T user) throws SQLException {
		checkTableExist(user);
		String preparedSql = sqlCash.getSqlToSaveClass(user.getClass());
		PreparedStatement preparedStatement = conn.prepareStatement(preparedSql, Statement.RETURN_GENERATED_KEYS);
		List<String> fieldsNames = sqlCash.getClassStructure(user.getClass());
		int indexInStatement = 1;
		for (String fieldName : fieldsNames) {
			Object value = ReflectionHelper.getFieldValue(user, fieldName);
			preparedStatement.setObject(indexInStatement, value);
			indexInStatement++;
		}
		preparedStatement.executeUpdate();
		ResultSet resultSet = preparedStatement.getGeneratedKeys();
		resultSet.next();
		long id =  ((BigInteger) resultSet.getObject("GENERATED_KEY")).longValue(); //
		resultSet.close();
		preparedStatement.close();
		ReflectionHelper.setFieldValue(user, "id", id);
		System.out.println("Save to DB & save created 'id' to object: " + user);
	}

	private <T extends DataSet> void checkTableExist(T user) throws SQLException{
		String tableName = null;
		Class<T> clazz = (Class<T>) user.getClass();
		if (clazz.isAnnotationPresent(TableName.class)) tableName = clazz.getAnnotation(TableName.class).tableName();
		if(!dbHelper.tableExist(tableName)) dbHelper.createTableByName(tableName);
	}

	@Override
	public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
		String sqlString = sqlCash.getSqlToLoadClass(clazz);
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
