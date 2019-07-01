package ru.otus.security;

import ru.otus.datasets.AccountDataSet;

import javax.servlet.http.HttpSession;
import java.util.*;

public class AppUtils {
	private static int REDIRECT_ID = 0;
	private static final Map<Integer, String> id_uri_map = new HashMap<>();
	private static final Map<String, Integer> uri_id_map = new HashMap<>();

	public static void storeLoginedUser(HttpSession session, AccountDataSet loginedUser) {
		session.setAttribute("loginedUser", loginedUser);
	}

	public static AccountDataSet getLoginedUser(HttpSession session) {
		AccountDataSet loginedUser = (AccountDataSet) session.getAttribute("loginedUser");
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
		return url;
	}
}
