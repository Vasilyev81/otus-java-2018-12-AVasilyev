package ru.otus.messageSystem.messages.toFrontend;

import ru.otus.frontend.FrontendService;
import ru.otus.messageSystem.Address;
import ru.otus.sockets.ChatSocket;

public class MsgGetAllMessagesAnswer extends MsgToFrontend {
	private final ChatSocket chatSocket;
	private final String messages;

	public MsgGetAllMessagesAnswer(Address address, Address frontAddress, ChatSocket chatSocket, String messages) {
		super(address, frontAddress);
		this.chatSocket = chatSocket;
		this.messages = messages;
	}

	@Override
	public void exec(FrontendService frontendService) {frontendService.returnAllMessages(chatSocket, messages);


	}
}
