package ru.otus.messageSystem.messages.toFrontend;

import ru.otus.datasets.UserDataSet;
import ru.otus.frontend.FrontendService;
import ru.otus.messageSystem.Address;

import java.util.List;

public class MsgGetAllUsersAnswer extends MsgToFrontend {
	private final String id;
	private List<UserDataSet> users;

	public MsgGetAllUsersAnswer(Address from, Address to, String id, List<UserDataSet> users) {
		super(from, to);
		this.id = id;
		this.users = users;
	}

	@Override
	public void exec(FrontendService frontendService) {
		frontendService.getListOfUsersSocket(id).sendAnswer(users);
	}
}
