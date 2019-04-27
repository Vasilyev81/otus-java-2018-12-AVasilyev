package ru.otus.l111.dbutils;


import ru.otus.l111.executor.OrmException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public abstract class DbHelper {
	private Connection conn;
	private final String DEFAULT_TABLE = "users";
	private Set<String> createdTables = new HashSet<>();

	public abstract Connection getConnection() throws SQLException;

	public abstract void createDefaultTable() throws SQLException;
	public abstract void getDbConnInfo() throws SQLException;

	public abstract void createTableByName(String tableName) throws SQLException;

	public abstract String createTableSQL(String tableName);

	public abstract void dropTableByName(String tableName) throws SQLException;

	public abstract void dropDefaultTable() throws SQLException;

	public abstract void dropAllTables() throws OrmException;

	public abstract String dropTableSQL(String tableName);

	public abstract void closeConnection() throws SQLException;

	public abstract String getDefaultTableName();

	public abstract boolean tableExist(String tableName);
}
