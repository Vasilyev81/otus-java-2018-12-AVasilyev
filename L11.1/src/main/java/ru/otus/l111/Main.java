package ru.otus.l111;

import ru.otus.l111.datasets.*;
import ru.otus.l111.dbservice.DBService;
import ru.otus.l111.dbservice.hibernate.DBServiceHibernateImpl;
import ru.otus.l111.dbservice.hibernate.hiberconfigs.MySqlConfiguration;

import java.util.*;

public class Main {
	private static DBService dbService;

	public static void main(String[] args) {

		dbService = new DBServiceHibernateImpl(new MySqlConfiguration());
		initiation();
	}

	public static void initiation() {
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

		UserDataSet dataSet = (UserDataSet) dbService.read(1);
		System.out.println("====================================\nread by ID");
		System.out.println(dataSet);

		dataSet = (UserDataSet) dbService.readByName("sully");
		System.out.println("====================================\nread by \"name\"");
		System.out.println(dataSet);

		List<UserDataSet> dataSets = dbService.readAll();
		System.out.println("====================================\nreadAll");
		for (UserDataSet userDataSet : dataSets) {
			System.out.println("User: " + userDataSet);
		}
		dbService.shutdown();
	}
}
