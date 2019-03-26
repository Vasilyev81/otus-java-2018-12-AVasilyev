package ru.otus.l101;

import ru.otus.l101.dao.UserDataSet;
import ru.otus.l101.dbutils.DbHelper;
import ru.otus.l101.executor.DbExecutor;
import ru.otus.l101.executor.DbExecutorImpl;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MyORM {
	private static DbExecutor executor;
	private static List<UserDataSet> userDataSetList;
	private static final DbHelper dbHelper = new DbHelper();

	public static void main(String[] args) throws SQLException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		initDB();
		saveUsers();
		loadUsers();
		printUserDataSetList();
		clearDB();
	}

	private static void initDB() throws SQLException {
		executor = new DbExecutorImpl(dbHelper.getConnection());
		dbHelper.createUsersTable();
	}

	private static void saveUsers() {
		final Random rnd = new Random();
		Arrays.asList("Andrey", "Sergey", "Maksim", "Ilya", "Fedor", "Dmitriy").forEach(x -> {
			try {
				executor.save(new UserDataSet(x, rnd.nextInt(99)));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	private static void loadUsers() throws InvocationTargetException, NoSuchMethodException, InstantiationException, SQLException, IllegalAccessException {
		userDataSetList = new ArrayList<>();
		for (long id = 1L; id < 7; id++) {
			userDataSetList.add(executor.load(id, UserDataSet.class));
		}
	}

	private static void printUserDataSetList() {
		for (UserDataSet user : userDataSetList) {
			System.out.println(user);
		}
	}

	private static void clearDB() throws SQLException {
		dbHelper.dropUsersTable();
	}
}
