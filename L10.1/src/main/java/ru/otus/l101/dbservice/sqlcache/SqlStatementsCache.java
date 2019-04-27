package ru.otus.l101.dbservice.sqlcache;

import ru.otus.l101.dao.DataSet;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlStatementsCache {
	private SqlStringFactory sqlFactory;
	private Map<Class, String> sqlToSaveClass;
	private Map<Class, String> sqlToLoadClass;

	public SqlStatementsCache(String tableName) {
		this.sqlFactory = new SqlStringFactory(tableName);
		this.sqlToSaveClass = new HashMap<>();
		this.sqlToLoadClass = new HashMap<>();
	}

	public <T extends DataSet> String getSqlToLoadClass(Class<T> clazz) {
		String result = sqlToLoadClass.get(clazz);
		if (result == null) {
			result = sqlFactory.createSqlToLoad(clazz);
			sqlToLoadClass.put(clazz, result);
		}
		return result;
	}

	public String getSqlToSaveClass(Class<? extends DataSet> clazz) throws SQLException {
		String sqlString = null;
		if (sqlToSaveClass.get(clazz) == null) {
			sqlString = sqlFactory.analyzeClass(clazz);
			sqlToSaveClass.put(clazz, sqlString);
		}
		return sqlToSaveClass.get(clazz);
	}

	public List<String> getClassStructure(Class<? extends DataSet> clazz) {
		return sqlFactory.getClassStructure(clazz);
	}

	public <T extends DataSet> void analyzeClass(Class<T> clazz) throws SQLException {
		sqlFactory.analyzeClass(clazz);
	}
}