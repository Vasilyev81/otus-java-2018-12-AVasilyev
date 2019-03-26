package ru.otus.l101.dbutils;

import java.sql.*;

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
	private final String createTableSQL = "CREATE TABLE IF NOT EXISTS users (id BIGINT(20) NOT NULL AUTO_INCREMENT KEY, name VARCHAR(255), age INT(3));";
	private final String dropTableSQL = "DROP TABLE IF EXISTS myorm.users;";

	public Connection getConnection() throws SQLException {
		assert conn != null;
		//conn = DriverManager.getConnection(sqLiteConnString);
		conn = DriverManager.getConnection(mySqlConnString);
		return conn;
	}

	public void createUsersTable() throws SQLException {
		Statement statement = conn.createStatement();
		statement.executeUpdate(createTableSQL);//Error: Incorrect table definition; there can be only one auto column and it must be defined as a key
	}

	public void dropUsersTable() throws SQLException {
		Statement statement = conn.createStatement();
		statement.executeUpdate(dropTableSQL);
	}
}
