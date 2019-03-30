package ru.otus.l101.executor;

import ru.otus.l101.dao.DataSet;
import ru.otus.l101.dao.TableName;
import ru.otus.l101.executor.reflection.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class DbExecutorHelper {
	private Map<Class, List<String>> classesStructure = new HashMap<>();
	private Map<Class, String> sqlToSaveClass;
	private Map<Class, String> sqlToLoadClass;

	DbExecutorHelper() {
		this.sqlToSaveClass = new HashMap<>();
		this.sqlToLoadClass = new HashMap<>();
	}

	<T extends DataSet> String getSqlToLoadClass(Class<T> clazz) {
		String result = sqlToLoadClass.get(clazz);
		if (result == null) {
			result = createSqlToLoad(clazz);
			sqlToLoadClass.put(clazz, result);
		}
		return result;
	}

	<T extends DataSet> void createSqlForSave(Class<T> clazz) {
		String tableName = null;
		if (clazz.isAnnotationPresent(TableName.class)) tableName = clazz.getAnnotation(TableName.class).tableName();
		String defaultTableName = "users";
		if(tableName == null) tableName = defaultTableName;
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
		sqlToSaveClass.put(clazz, sqlForSave.toString());
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

	String getSqlToSaveClass(Class<? extends DataSet> clazz) {
		if (sqlToSaveClass.get(clazz) == null) analyzeClass(clazz);
		return sqlToSaveClass.get(clazz);
	}

	List<String> getClassStructure(Class<? extends DataSet> clazz) {
		return classesStructure.get(clazz);
	}

	<T extends DataSet> void analyzeClass(Class<T> clazz) {
		List<String> declaredFields = Stream.of(clazz.getDeclaredFields()).map(Field::getName).collect(Collectors.toList());
		addToClassesStructures(clazz, declaredFields);
		Class superClazz = clazz.getSuperclass();
		if (superClazz != Object.class) analyzeClass(superClazz);
		createSqlForSave(clazz);
	}
}