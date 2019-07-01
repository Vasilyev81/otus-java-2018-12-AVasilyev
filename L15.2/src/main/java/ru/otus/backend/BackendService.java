package ru.otus.backend;

import ru.otus.backend.chatService.dto.ChatMessage;
import ru.otus.datasets.DataSet;
import ru.otus.messageSystem.Addressee;
import ru.otus.sockets.ChatSocket;

import java.util.List;

public interface BackendService extends Addressee {
	String getDBStatus();

	<T extends DataSet> void save(List<T> dataSet);

	<T extends DataSet> void save(T dataSet);

	void read(long id, String sessionId);

	void readAll(String sessionId);

	void readByName(String name, String sessionId);

	void getAccounts(String requestId);

	void addChatMessage(ChatMessage message);

	void getAllChatMessages(ChatSocket chatSocket);

	void shutdown();
}