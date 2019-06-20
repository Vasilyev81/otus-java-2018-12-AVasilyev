package ru.otus.messageSystem.messages.toFrontend;

import ru.otus.datasets.UserDataSet;
import ru.otus.messageSystem.Address;
import ru.otus.servlets.FrontendService;

import java.util.List;
import java.util.logging.Logger;

public class MsgGetAllUsersAnswer extends MsgToFrontend {
	private List<UserDataSet> userDataSetList;
	Logger log = Logger.getLogger(MsgGetAllUsersAnswer.class.getName());

	public MsgGetAllUsersAnswer(Address to, Address from, List<UserDataSet> userDataSetList) {
		super(to, from);
		this.userDataSetList = userDataSetList;
	}

	@Override
	public void exec(FrontendService frontendService) {
		frontendService.setResultList(userDataSetList);
	}
}
