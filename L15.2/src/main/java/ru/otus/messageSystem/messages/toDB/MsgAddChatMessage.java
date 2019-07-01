package ru.otus.messageSystem.messages.toDB;

import ru.otus.backend.BackendService;
import ru.otus.backend.chatService.dto.ChatMessage;
import ru.otus.messageSystem.Address;

public class MsgAddChatMessage extends MsgToBackend {
	private final ChatMessage message;

	public MsgAddChatMessage(Address address, Address dbAddress, ChatMessage message) {
		super(address, dbAddress);
		this.message = message;
	}

	@Override
	public void exec(BackendService backendService) {
		backendService.addChatMessage(message);
	}
}
