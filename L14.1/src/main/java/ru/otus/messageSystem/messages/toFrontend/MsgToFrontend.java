package ru.otus.messageSystem.messages.toFrontend;

import ru.otus.messageSystem.Address;
import ru.otus.messageSystem.Addressee;
import ru.otus.messageSystem.messages.Message;
import ru.otus.servlets.FrontendService;

public abstract class MsgToFrontend extends Message {
	public MsgToFrontend(Address from, Address to) {
		super(from, to);
	}

	@Override
	public void exec(Addressee addressee) {
		if (addressee instanceof FrontendService) {
			exec((FrontendService) addressee);
		} else {
			//todo error!
		}
	}

	public abstract void exec(FrontendService frontendService);
}