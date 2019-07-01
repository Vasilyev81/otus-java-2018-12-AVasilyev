package ru.otus.messageSystem.messages.toDB;

import ru.otus.backend.BackendService;
import ru.otus.datasets.DataSet;
import ru.otus.messageSystem.Address;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MsgSaveUser extends MsgToBackend {
	private final List<DataSet> dataSets;

	public <T extends DataSet> MsgSaveUser(Address address, Address dbAddress, T... dataSet) {
		super(address, dbAddress);
		this.dataSets = new ArrayList();
		this.dataSets.addAll(Arrays.asList(dataSet));
	}

	@Override
	public void exec(BackendService backendService) {
		backendService.save(dataSets);
	}
}
