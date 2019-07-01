package ru.otus.messageSystem.messages.toDB;

import ru.otus.backend.BackendService;
import ru.otus.messageSystem.Address;
import ru.otus.sockets.ChatSocket;

public class MsgGetAllMessages extends MsgToBackend {
	private final ChatSocket chatSocket;
	public MsgGetAllMessages(Address address, Address dbAddress, ChatSocket chatSocket) {
		super(address, dbAddress);
		this.chatSocket = chatSocket;
	}

	@Override
	public void exec(BackendService backendService) {
		backendService.getAllChatMessages(chatSocket);
	}
}
