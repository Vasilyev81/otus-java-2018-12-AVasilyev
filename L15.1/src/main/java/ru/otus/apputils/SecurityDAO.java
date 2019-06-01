package ru.otus.apputils;

import java.util.*;

public class SecurityDAO {
	private static final Map<String, Account> mapUsers = new HashMap<String, Account>();

	static {
		initUsers();
	}

	private static void initUsers() {
		Account admin = new Account("admin", "123", SecurityConfig.ROLE_ADMINISTRATOR);
		mapUsers.put(admin.getLogin(), admin);
	}

	public static Account findUser(String login, String password) {
		Account u = mapUsers.get(login);
		if (u != null && u.getPassword().equals(password)) {
			return u;
		}
		return null;
	}
}
