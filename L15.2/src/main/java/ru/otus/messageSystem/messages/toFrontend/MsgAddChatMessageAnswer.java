package ru.otus.messageSystem.messages.toFrontend;

import ru.otus.frontend.FrontendService;
import ru.otus.messageSystem.Address;

public class MsgAddChatMessageAnswer extends MsgToFrontend {
	private final String text;

	public MsgAddChatMessageAnswer(Address address, Address frontAddress, String text) {
		super(address, frontAddress);this.text = text;
	}

	@Override
	public void exec(FrontendService frontendService) {
		frontendService.informAll(text);
	}
}
