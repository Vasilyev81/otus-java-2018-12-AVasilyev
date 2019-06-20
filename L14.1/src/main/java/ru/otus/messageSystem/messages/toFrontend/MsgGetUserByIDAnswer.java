package ru.otus.messageSystem.messages.toFrontend;

import ru.otus.datasets.UserDataSet;
import ru.otus.messageSystem.Address;
import ru.otus.servlets.FrontendService;

public class MsgGetUserByIDAnswer extends MsgToFrontend {
	private final UserDataSet dataSet;
	public MsgGetUserByIDAnswer(Address to, Address from, UserDataSet dataSet) {
		super(to, from);
		this.dataSet = dataSet;
	}

	@Override
	public void exec(FrontendService frontendService) {
		frontendService.returnUserResult(dataSet);
	}
}
