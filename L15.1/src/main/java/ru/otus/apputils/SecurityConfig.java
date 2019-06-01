package ru.otus.apputils;

import java.util.*;

public class SecurityConfig {
	public static final String ROLE_ADMINISTRATOR = "ADMINISTRATOR";
	private static final Map<String, List<String>> mapConfig = new HashMap<>();

	static {
		init();
	}

	private static void init() {
		List<String> urlPatterns1 = new ArrayList<String>();
		urlPatterns1.addAll(Arrays.asList("/admin_page/saveuser", "/admin_page/finduser", "/admin_page/listofusers"));
		mapConfig.put(ROLE_ADMINISTRATOR, urlPatterns1);
	}

	public static Set<String> getAllAppRoles() {
		return mapConfig.keySet();
	}

	public static List<String> getUrlPatternsForRole(String role) {
		return mapConfig.get(role);
	}
}
