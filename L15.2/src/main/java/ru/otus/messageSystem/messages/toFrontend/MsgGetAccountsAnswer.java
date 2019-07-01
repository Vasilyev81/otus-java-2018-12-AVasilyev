package ru.otus.messageSystem.messages.toFrontend;

import ru.otus.datasets.AccountDataSet;
import ru.otus.frontend.FrontendService;
import ru.otus.messageSystem.Address;

import java.util.List;

public class MsgGetAccountsAnswer extends MsgToFrontend {
	private String requestId;
	private List<AccountDataSet> users;

	public MsgGetAccountsAnswer(Address address, Address frontAddress, String requestId, List<AccountDataSet> users) {
		super(address, frontAddress);
		this.requestId = requestId;
		this.users = users;
	}

	@Override
	public void exec(FrontendService frontendService) {
		frontendService.getSecurityDao().setAccounts(requestId, users);
	}
}