package ru.otus.messageSystem.messages.toDB;

import ru.otus.backend.BackendService;
import ru.otus.messageSystem.Address;

public class MsgGetUserByID extends MsgToBackend {
	private String sessionId;
	private long id;

	public MsgGetUserByID(Address address, Address dbAddress, long id, String sessionId) {
		super(address, dbAddress);
		this.id = id;
		this.sessionId = sessionId;
	}

	@Override
	public void exec(BackendService backendService) {
		backendService.read(id, sessionId);
	}
}
