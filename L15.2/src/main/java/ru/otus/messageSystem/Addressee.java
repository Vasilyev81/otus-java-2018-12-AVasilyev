package ru.otus.messageSystem;

public interface Addressee {
	Address getAddress();

	void setContext(MessageSystemContext context);

	MessageSystemContext getContext();
}
