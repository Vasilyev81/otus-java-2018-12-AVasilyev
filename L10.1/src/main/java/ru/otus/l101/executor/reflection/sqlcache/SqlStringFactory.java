package ru.otus.l101.executor.reflection.sqlcache;

import ru.otus.l101.dao.DataSet;
import ru.otus.l101.dao.TableName;
import ru.otus.l101.executor.reflection.ReflectionHelper;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

class SqlStringFactory {
	private final String DEFAULT_TABLE;
	private Map<Class, List<String>> classesStructure;

	public SqlStringFactory(String tableName) {
		this.DEFAULT_TABLE = tableName;
		this.classesStructure = new HashMap<>();
	}

	<T extends DataSet> String createSqlForSave(Class<T> clazz) throws SQLException {
		String tableName = null;
		if (clazz.isAnnotationPresent(TableName.class)) tableName = clazz.getAnnotation(TableName.class).tableName();
		if(tableName == null) tableName = DEFAULT_TABLE;
		List<String> fieldsNames = classesStructure.get(clazz);
		String allFieldsNames = String.join(", ", fieldsNames);
		String valuePlaceHolders = Stream.generate(() -> "?").limit(fieldsNames.size()).collect(Collectors.joining(", "));
		StringBuilder sqlForSave = new StringBuilder();
		sqlForSave.append("INSERT INTO ")
				.append(tableName)
				.append(" (")
				.append(allFieldsNames)
				.append(") ")
				.append("VALUES ")
				.append("(")
				.append(valuePlaceHolders)
				.append(");");
		return sqlForSave.toString();
	}

	<T extends DataSet> String createSqlToLoad(Class<T> clazz) {
		String tableName = getTableName(clazz);
		List<String> fieldsNames = ReflectionHelper.getAllFieldsNames(clazz);
		String fields = fieldsNames.stream().collect(joining(", "));
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ")
				.append(fields)
				.append(" FROM ")
				.append(tableName)
				.append(" WHERE id = ?;")
		;
		return sb.toString();
	}

	private <T extends DataSet> String getTableName(Class<T> clazz) {
		String tableName = null;
		if (clazz.isAnnotationPresent(TableName.class)) {
			tableName = clazz.getAnnotation(TableName.class).tableName();
		}
		return tableName;
	}

	<T extends DataSet> void addToClassesStructures(Class<T> clazz, List<String> declaredFields) {
		classesStructure.put(clazz, declaredFields);
	}

	<T extends DataSet> String analyzeClass(Class<T> clazz) throws SQLException {
		List<String> declaredFields = Stream.of(clazz.getDeclaredFields()).map(Field::getName).collect(Collectors.toList());
		addToClassesStructures(clazz, declaredFields);
		Class superClazz = clazz.getSuperclass();
		if (superClazz != Object.class) analyzeClass(superClazz);
		String sqlString = createSqlForSave(clazz);
		return sqlString;
	}

	List<String> getClassStructure(Class<? extends DataSet> clazz) {
		return classesStructure.get(clazz);
	}
}