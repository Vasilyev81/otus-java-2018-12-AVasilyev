package ru.otus.l101.dbutils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class DbService {
	private Connection conn;

	public DbService(Connection conn) {
		this.conn = conn;
	}

	public void getDbConnInfo() throws SQLException {
		DatabaseMetaData dbMeta = conn.getMetaData();
		System.out.println("Driver name is: " + dbMeta.getDriverName() +
				"\nDatabase ver." + dbMeta.getDatabaseProductVersion() +
				"\nDatabaseProductName: " + dbMeta.getDatabaseProductName() +
				"\nDatabase autocommit: " + conn.getAutoCommit());
	}
}
