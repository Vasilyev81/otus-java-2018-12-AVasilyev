package ru.otus.apputils;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class AppUtils {
	private static int REDIRECT_ID = 0;
	private static final Map<Integer, String> id_uri_map = new HashMap<>();
	private static final Map<String, Integer> uri_id_map = new HashMap<>();

	public static void storeLoginedUser(HttpSession session, Account loginedUser) {
		session.setAttribute("loginedUser", loginedUser);
	}

	public static Account getLoginedUser(HttpSession session) {
		Account loginedUser = (Account) session.getAttribute("loginedUser");
		return loginedUser;
	}

	public static int storeRedirectURL(String requestUri) {
		Integer id = uri_id_map.get(requestUri);
		if (id == null) {
			id = REDIRECT_ID++;
			uri_id_map.put(requestUri, id);
			id_uri_map.put(id, requestUri);
			return id;
		}
		return id;
	}

	public static String getRedirectUrl(int redirectId) {
		String url = id_uri_map.get(redirectId);
		if (url != null) {
			return url;
		}
		return null;
	}
}
