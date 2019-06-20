package ru.otus.messageSystem.messages.toDB;

import ru.otus.dao.DBService;
import ru.otus.datasets.DataSet;
import ru.otus.messageSystem.Address;
import ru.otus.messageSystem.messages.toDB.MsgToDB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MsgSaveUser extends MsgToDB {
	private final List<DataSet> dataSets;

	public <T extends DataSet> MsgSaveUser(Address address, Address dbAddress, T... dataSet) {
		super(address, dbAddress);
		this.dataSets = new ArrayList();
		this.dataSets.addAll(Arrays.asList(dataSet));
	}

	@Override
	public void exec(DBService dbService) {
		dbService.save(dataSets);
		//dbService.getMessageSystem().sendMessage(new MsgSaveUserAnswer(getTo(), getFrom()));
	}
}
