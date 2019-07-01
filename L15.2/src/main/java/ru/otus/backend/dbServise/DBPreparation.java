package ru.otus.backend.dbServise;

import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.datasets.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DBPreparation {
	@Autowired
	public DBPreparation(DBService dbService) {
		UserDataSet user1 = new UserDataSet("tully", AccountDataSet.getUserAccount("tully", "1234"), Collections.singletonList(new AddressDataSet("Mira")),
				new PhoneDataSet("+1 234 567 8018"), new PhoneDataSet("+7 987 645 4545"));
		List<AddressDataSet> addresses = new ArrayList<>();
		addresses.add(new AddressDataSet("Truda"));
		addresses.add(new AddressDataSet("Moskovskaya"));

		UserDataSet user2 = new UserDataSet(
				"sully", AccountDataSet.getUserAccount("sully", "1234"),
				addresses,
				new PhoneDataSet("+67 890 344 4422")
		);
		UserDataSet user3 = new UserDataSet(
				"bobby", AccountDataSet.getAdminAccount("admin", "123"),
				Collections.singletonList(new AddressDataSet("Borskayaa")),
				new PhoneDataSet("+54 500 711 2088")
		);
		UserDataSet user4 = new UserDataSet(
				"molly", AccountDataSet.getAdminAccount("molly", "1234"),
				Arrays.asList(new AddressDataSet("Krasnaya"), new AddressDataSet("Podvodnikov")),
				new PhoneDataSet("+99 000 999 8709")
		);


		CompanyDataSet anex = new CompanyDataSet("Anex");
		CompanyDataSet biblio = new CompanyDataSet("Biblio");
		dbService.save(Arrays.asList(anex, biblio));

		anex.addEmployees(Arrays.asList(user1, user2));
		biblio.addEmployees(Arrays.asList(user3, user4));

		dbService.save(Arrays.asList(user1, user2, user3, user4));
	}
}
