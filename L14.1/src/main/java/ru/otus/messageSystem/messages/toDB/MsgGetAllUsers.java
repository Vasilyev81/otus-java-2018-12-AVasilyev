package ru.otus.messageSystem.messages.toDB;

import ru.otus.dao.DBService;
import ru.otus.datasets.UserDataSet;
import ru.otus.messageSystem.Address;
import ru.otus.messageSystem.messages.toFrontend.MsgGetAllUsersAnswer;

import java.util.List;
import java.util.logging.Logger;

public class MsgGetAllUsers extends MsgToDB {
	Logger log = Logger.getLogger(MsgGetAllUsers.class.getName());
	public MsgGetAllUsers(Address address, Address dbAddress) {
		super(address,dbAddress);
	}

	@Override
	public void exec(DBService dbService) {
		List<UserDataSet> userDataSetList = dbService.readAll();
		dbService.getMessageSystem().sendMessage(new MsgGetAllUsersAnswer(getTo(),getFrom(), userDataSetList));
		log.info("Message from DB was sent to message system");
	}
}
