package ru.otus.security;

import java.util.*;

public class SecurityConfig {
	public static final String ROLE_ADMINISTRATOR = "ADMINISTRATOR";
	public static final String ROLE_USER = "USER";
	private static final Map<String, List<String>> mapConfig = new HashMap<>();

	static {
		init();
	}

	private static void init() {
		List<String> urlPatternsAdmin = new ArrayList<>();
		urlPatternsAdmin.addAll(Arrays.asList("/admin_page/save_user", "/admin_page/find_user", "/admin_page/users_list"));
		mapConfig.put(ROLE_ADMINISTRATOR, urlPatternsAdmin);
		List<String> urlPatternsUser = new ArrayList<>();
		urlPatternsUser.addAll(Arrays.asList("/admin_page/find_user", "/admin_page/users_list"));
		mapConfig.put(ROLE_USER, urlPatternsUser);
	}

	public static Set<String> getAllRoles() {
		return mapConfig.keySet();
	}

	public static List<String> getUrlPatternsForRole(String role) {
		return mapConfig.get(role);
	}
}
