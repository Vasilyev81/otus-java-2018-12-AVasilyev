package ru.otus.messageSystem.messages.toDB;

import org.hibernate.ObjectNotFoundException;
import ru.otus.dao.DBService;
import ru.otus.datasets.UserDataSet;
import ru.otus.messageSystem.Address;
import ru.otus.messageSystem.messages.toDB.MsgToDB;
import ru.otus.messageSystem.messages.toFrontend.MsgGetUserByIDAnswer;

public class MsgGetUserByID extends MsgToDB {
	private long id;

	public MsgGetUserByID(Address address, Address dbAddress, long id) {
		super(address, dbAddress);
		this.id = id;
	}

	@Override
	public void exec(DBService dbService) {
		UserDataSet dataSet = null;
		try {
			dataSet = dbService.read(id);
		} catch (ObjectNotFoundException ex) {
			System.err.println("Exception while retrieving user with id " + id + " from database, " + ex.getMessage());
		}
		dbService.getMessageSystem().sendMessage(new MsgGetUserByIDAnswer(getTo(), getFrom(), dataSet));
	}
}
