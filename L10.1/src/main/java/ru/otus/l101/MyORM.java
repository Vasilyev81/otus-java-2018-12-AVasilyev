package ru.otus.l101;

import ru.otus.l101.dao.DataSet;
import ru.otus.l101.dao.UserDataSet;
import ru.otus.l101.executor.ORM;
import ru.otus.l101.executor.OrmException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class MyORM {
	private static ORM orm;
	private static ArrayList<UserDataSet> userDataSetList;

	public static void main(String[] args) {
		orm = new ORM();
		initDB();
		preLoadClassStructure();
		createAndPushUsersToOrm();
		loadUsersById();
		printUserDataSetList();
		clearDB();
	}

	private static void clearDB() {
		try {
			orm.clearDB();
		} catch (OrmException e) {
			e.printStackTrace();
		}
	}

	private static void initDB() {
		try {
			orm.initDB();
		} catch (OrmException e) {
			e.printStackTrace();
		}
	}

	private static void printUserDataSetList() {
		userDataSetList.forEach(System.out::println);
	}

	private static void loadUsersById() {
		userDataSetList = LongStream.rangeClosed(1L, 6L).mapToObj((x) -> {
			UserDataSet result = null;
			try {
				result = orm.loadUsersById(x, UserDataSet.class);
			} catch (OrmException e) {
				e.printStackTrace();
			}
			return result;
		}).collect(Collectors.toCollection(ArrayList::new));
	}

	private static void createAndPushUsersToOrm() {
		final Random rnd = new Random();
		List<String> names= Arrays.asList("Andrey", "Sergey", "Maksim", "Ilya", "Fedor", "Dmitriy");
		for (String name: names) {
			try {
				orm.saveUser(new UserDataSet(name, rnd.nextInt(99)));
			} catch (OrmException e) {
				e.printStackTrace();
			}
		}
	}

	private static void preLoadClassStructure() {
		try {
			orm.loadClassStructure(UserDataSet.class);
			orm.loadClassStructure(DataSet.class);
		} catch (OrmException e) {
			e.getMessage();
		}
	}
}
