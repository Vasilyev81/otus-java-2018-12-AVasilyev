package ru.otus.messageSystem.messages.toDB;

import ru.otus.backend.BackendService;
import ru.otus.messageSystem.Address;

public class MsgGetAccounts extends MsgToBackend {
	private String requestId;

	public MsgGetAccounts(Address address, Address dbAddress, String requestId) {
		super(address, dbAddress);
		this.requestId = requestId;
	}

	@Override
	public void exec(BackendService backendService) {
		backendService.getAccounts(requestId);
	}
}
