package ru.otus.l101.dbservice;

import ru.otus.l101.dbservice.executor.OrmException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PostgresDbHelper extends DbHelper {
	private Connection conn;
	private final String postgresConnString = "jdbc:postgresql://localhost:5432/" +
			"hibernate" +
			"user=postgres&" +
			"password=postgres";
	private final String DEFAULT_TABLE = "users";
	private Set<String> createdTables = new HashSet<>();

	public Connection getConnection() throws SQLException {
		conn = DriverManager.getConnection(postgresConnString);
		return conn;
	}

	public void createDefaultTable() throws SQLException {
		createTableByName(DEFAULT_TABLE);
	}
	public void getDbConnInfo() throws SQLException {
		DatabaseMetaData dbMeta = conn.getMetaData();
		System.out.println("Driver name is: " + dbMeta.getDriverName() +
				"\nDatabase ver." + dbMeta.getDatabaseProductVersion() +
				"\nDatabaseProductName: " + dbMeta.getDatabaseProductName() +
				"\nDatabase autocommit: " + conn.getAutoCommit());
	}

	public void createTableByName(String tableName) throws SQLException {
		Statement statement = conn.createStatement();
		statement.executeUpdate(createTableSQL(tableName));
		createdTables.add(tableName);
		statement.close();
	}

	public String createTableSQL(String tableName) {
		StringBuilder sqlString = new StringBuilder()
				.append("CREATE TABLE IF NOT EXISTS ")
				.append(tableName)
				.append(" (id BIGSERIAL NOT NULL PRIMARY KEY, name VARCHAR(255), age SMALLINT);");
		return sqlString.toString();
	}

	public void dropTableByName(String tableName) throws SQLException {
		Statement statement = conn.createStatement();
		statement.executeUpdate(dropTableSQL(tableName));
		createdTables.remove(tableName);
		statement.close();
	}

	public void dropDefaultTable() throws SQLException {
		dropTableByName(DEFAULT_TABLE);
	}

	public void dropAllTables() throws OrmException {
		List<String> tablesNamesList = new ArrayList<>(createdTables);
		try {
			for (String tableName : tablesNamesList) dropTableByName(tableName);
		} catch (SQLException e) {
			throw new OrmException(e);
		}
	}

	public String dropTableSQL(String tableName) {
		StringBuilder sqlString = new StringBuilder()
				.append("DROP TABLE IF EXISTS ")
				.append(tableName).append(";");
		return sqlString.toString();
	}

	public void closeConnection() throws SQLException {
		conn.close();
	}

	public String getDefaultTableName() {
		return DEFAULT_TABLE;
	}

	public boolean tableExist(String tableName) {
		return createdTables.contains(tableName);
	}
}
