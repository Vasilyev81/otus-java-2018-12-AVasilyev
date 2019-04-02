package ru.otus.l101.dbutils;

import ru.otus.l101.executor.OrmException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DbHelper {
	private Connection conn;
	//private final String sqLiteConnString = "jdbc:sqlite:C:/sqlite/db/users.db";
	private final String mySqlConnString = "jdbc:mysql://localhost:3306/" +
			"MyORM?" +
			"user=root&" +
			"password=e4rt56yhu8&" +
			"verifyServerCertificate=false&" +
			"useSSL=false&" +
			"requireSSL=false&" +
			"useLegacyDatetimeCode=false&" +
			"amp&serverTimezone=UTC";
	private final String DEFAULT_TABLE = "users";
	private Set<String> createdTables = new HashSet<>();

	public Connection getConnection() throws SQLException {
		assert conn != null;
		//conn = DriverManager.getConnection(sqLiteConnString);
		conn = DriverManager.getConnection(mySqlConnString);
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

	private String createTableSQL(String tableName) {
		StringBuilder sqlString = new StringBuilder()
				.append("CREATE TABLE IF NOT EXISTS ")
				.append(tableName)
				.append("(id BIGINT(20) NOT NULL AUTO_INCREMENT KEY, name VARCHAR(255), age INT(3));");
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

	private String dropTableSQL(String tableName) {
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
