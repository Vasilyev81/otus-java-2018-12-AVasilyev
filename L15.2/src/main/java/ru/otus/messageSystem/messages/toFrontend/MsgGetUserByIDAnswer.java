package ru.otus.messageSystem.messages.toFrontend;

import ru.otus.datasets.UserDataSet;
import ru.otus.frontend.FrontendService;
import ru.otus.messageSystem.Address;

import java.util.List;

public class MsgGetUserByIDAnswer extends MsgToFrontend {
	private final UserDataSet user;
	private String sessionId;

	public MsgGetUserByIDAnswer(Address from, Address to, UserDataSet user, String sessionId) {
		super(from, to);
		this.user = user;
		this.sessionId = sessionId;
	}

	@Override
	public void exec(FrontendService frontendService) {
		frontendService.getFindUserServlet(sessionId).sendMessage(user);
	}
}
