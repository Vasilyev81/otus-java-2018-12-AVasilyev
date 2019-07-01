package ru.otus.messageSystem.messages.toDB;

import ru.otus.backend.BackendService;
import ru.otus.messageSystem.Address;

import java.util.logging.Logger;

public class MsgGetAllUsers extends MsgToBackend {
	private final String sessionId;
	Logger log = Logger.getLogger(MsgGetAllUsers.class.getName());
	public MsgGetAllUsers(Address address, Address dbAddress, String sessionId) {
		super(address,dbAddress);
		this.sessionId = sessionId;
	}

	@Override
	public void exec(BackendService backendService) {
		backendService.readAll(sessionId);
	}
}
