package ru.otus.frontend;

import ru.otus.backend.chatService.dto.ChatMessage;
import ru.otus.datasets.DataSet;
import ru.otus.messageSystem.Addressee;
import ru.otus.security.SecurityDAO;
import ru.otus.sockets.ChatSocket;
import ru.otus.sockets.FindUserSocket;
import ru.otus.sockets.ListOfUsersSocket;

public interface FrontendService extends Addressee {
	void readAll(String id);

	ListOfUsersSocket getListOfUsersSocket(String id);

	<T extends DataSet> void save(T... companyDataSet);

	void findUserByID(long id, String sessionId);

	void getAccounts(String requestId);

	void addChatMessage(ChatMessage message);

	void informAll(String text);

	void register(ChatSocket chatSocket);

	void unregister(ChatSocket chatSocket);

	void returnAllMessages(ChatSocket chatSocket, String messages);

	void register(String sessionId, ListOfUsersSocket listOfUsersSocket);

	void register(String sessionId, FindUserSocket findUserSocket);

	void unregister(String sessionId);

	SecurityDAO getSecurityDao();

	void setSecurityDao(SecurityDAO securityDao);

	FindUserSocket getFindUserServlet(String sessionId);
}

