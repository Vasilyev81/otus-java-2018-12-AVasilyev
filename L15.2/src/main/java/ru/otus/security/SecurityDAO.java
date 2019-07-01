package ru.otus.security;

import ru.otus.datasets.AccountDataSet;
import ru.otus.frontend.FrontendService;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class SecurityDAO {
	private FrontendService frontendService;
	private ConcurrentHashMap<String, AccountDataSet> accounts;

	public SecurityDAO(FrontendService frontend) {
		accounts = new ConcurrentHashMap<>();
		frontendService = frontend;

	}

	public void init(){
		frontendService.setSecurityDao(this);
		initFromDbRequest();
	}

	public void initFromDbRequest(){
		frontendService.getAccounts("0");
	}

	public AccountDataSet findUser(String login, String password) {
		if(accounts.isEmpty()) initFromDbRequest();
		AccountDataSet account = accounts.get(login);
		if (account != null && account.getPassword().equals(password)) {
			return account;
		}
		return null;
	}

	public void setAccounts(String requestId, List<AccountDataSet> users) {
		users.forEach(acc -> accounts.put(acc.getLogin(), acc));
	}
}
