package ru.otus.dao;

import ru.otus.dao.DBService;
import ru.otus.dao.DBServiceImpl;
import ru.otus.datasets.AddressDataSet;
import ru.otus.datasets.CompanyDataSet;
import ru.otus.datasets.PhoneDataSet;
import ru.otus.datasets.UserDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//public class DbMain {
//    public static void main(String[] args) {

public class DBPreparation {
	public DBService dbService;

	public DBPreparation() {
		dbService = new DBServiceImpl();
		initiation();
	}

	public DBService getDbService() {
		return dbService;
	}

	public void initiation() {
		String status = dbService.getLocalStatus();
		System.out.println("Status: " + status);
		UserDataSet user1 = new UserDataSet("tully", Collections.singletonList(new AddressDataSet("Mira")),
				new PhoneDataSet("+1 234 567 8018"), new PhoneDataSet("+7 987 645 4545"));

		List<AddressDataSet> addresses = new ArrayList<>();
		addresses.add(new AddressDataSet("Truda"));
		addresses.add(new AddressDataSet("Moskovskaya"));

		UserDataSet user2 = new UserDataSet(
				"sully",
				addresses,
				new PhoneDataSet("+67 890 344 4422")
		);
		UserDataSet user3 = new UserDataSet(
				"bobby",
				Collections.singletonList(new AddressDataSet("Borskayaa")),
				new PhoneDataSet("+54 500 711 2088")
		);
		UserDataSet user4 = new UserDataSet(
				"molly",
				Arrays.asList(new AddressDataSet("Krasnaya"), new AddressDataSet("Podvodnikov")),
				new PhoneDataSet("+99 000 999 8709")
		);

		CompanyDataSet anex = new CompanyDataSet("Anex");
		CompanyDataSet biblio = new CompanyDataSet("Biblio");
		dbService.save(anex);
		dbService.save(biblio);

		anex.addEmployees(Arrays.asList(user1, user2));
		biblio.addEmployees(Arrays.asList(user3, user4));
		System.out.println(user1);
		System.out.println(user2);
		System.out.println(user3);
		System.out.println(user4);

		dbService.save(user1);
		dbService.save(user2);
		dbService.save(user3);
		dbService.save(user4);

		UserDataSet dataSet = dbService.read(1);
		System.out.println("====================================\nread by ID");
		System.out.println(dataSet);

		dataSet = dbService.readByName("sully");
		System.out.println("====================================\nread by \"name\"");
		System.out.println(dataSet);

		List<UserDataSet> dataSets = dbService.readAll();
		System.out.println("====================================\nreadAll");
		for (UserDataSet userDataSet : dataSets) {
			System.out.println("User: " + userDataSet);
		}
	}
}
