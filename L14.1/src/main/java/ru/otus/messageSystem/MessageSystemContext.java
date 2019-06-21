package ru.otus.messageSystem;

import ru.otus.dao.DBService;
import ru.otus.servlets.FrontendService;

public class MessageSystemContext {
    private final MessageSystem messageSystem;

    private Address frontAddress;
    private Address dbAddress;

    public MessageSystemContext(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
    }

	public void init() {
		messageSystem.start();
	}

	public MessageSystem getMessageSystem() {
        return messageSystem;
    }

	public void setFrontAddress(FrontendService service) {
		this.frontAddress  = service.getAddress();
		messageSystem.addAddressee(service);
	}

	public void setDbAddress(DBService service) {
		this.dbAddress = service.getAddress();
		messageSystem.addAddressee(service);
	}

	public Address getFrontAddress() {
		return frontAddress;
	}

    public Address getDbAddress() {
        return dbAddress;
    }
}
