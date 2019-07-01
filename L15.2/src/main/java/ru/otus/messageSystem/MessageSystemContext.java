package ru.otus.messageSystem;

import ru.otus.backend.BackendService;
import ru.otus.frontend.FrontendService;

import java.util.HashMap;
import java.util.Map;

public class MessageSystemContext {
	private MessageSystem messageSystem;
	private Map<Address, Addressee> addresseeMap;
	private Address frontAddress;
	private Address backendAddress;

	public MessageSystemContext(FrontendService frontend, BackendService backend) {
		addresseeMap = new HashMap<>();
		setFrontendService(frontend);
		setBackendService(backend);
	}

	public void setFrontendService(FrontendService frontend) {
		frontend.setContext(this);
		Address address = frontend.getAddress();
		this.frontAddress = address;
		addresseeMap.put(address, frontend);
	}

	public Address getFrontAddress() {
		return frontAddress;
	}

	public void setBackendService(BackendService backend) {
		backend.setContext(this);
		Address address = backend.getAddress();
		this.backendAddress = address;
		addresseeMap.put(address, backend);
	}

	public MessageSystem getMessageSystem() {
		return messageSystem;
	}

	public Address getBackendAddress() {
		return backendAddress;
	}
	public Map<Address, Addressee> getAddresseeMap() {
		return addresseeMap;
	}

	public void setMessageSystem(MessageSystem messageSystem) {
		this.messageSystem = messageSystem;
	}
}
