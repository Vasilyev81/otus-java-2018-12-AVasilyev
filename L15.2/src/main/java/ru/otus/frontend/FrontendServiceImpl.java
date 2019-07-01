package ru.otus.frontend;

import ru.otus.backend.chatService.dto.ChatMessage;
import ru.otus.datasets.DataSet;
import ru.otus.messageSystem.Address;
import ru.otus.messageSystem.MessageSystemContext;
import ru.otus.messageSystem.messages.Message;
import ru.otus.messageSystem.messages.toDB.*;
import ru.otus.security.SecurityDAO;
import ru.otus.sockets.ChatSocket;
import ru.otus.sockets.FindUserSocket;
import ru.otus.sockets.ListOfUsersSocket;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

public class FrontendServiceImpl implements FrontendService {
	private final Address address;
	private MessageSystemContext context;
	private static final CopyOnWriteArrayList<ChatSocket> sockets = new CopyOnWriteArrayList<>();
	private static final ConcurrentHashMap<String, ListOfUsersSocket> listOfUsersSockets = new ConcurrentHashMap<>();
	private static final  ConcurrentHashMap<String, FindUserSocket> findUserSockets = new ConcurrentHashMap<>();
	private static SecurityDAO securityDao;

	private static final Logger log = Logger.getLogger(FrontendServiceImpl.class.getName());

	public FrontendServiceImpl(Address address) {
		this.address = address;
	}

	@Override
	public void setContext(MessageSystemContext context) {
		this.context = context;
	}

	@Override
	public MessageSystemContext getContext() {
		return context;
	}

	@Override
	public void readAll(String sessionId) {
		Message msg = new MsgGetAllUsers(this.address, context.getBackendAddress(), sessionId);
		context.getMessageSystem().sendMessage(msg);
	}

	@Override
	public ListOfUsersSocket getListOfUsersSocket(String id) {
		return listOfUsersSockets.get(id);
	}

	@Override
	public <T extends DataSet> void save(T... dataSet) {
		Message msg = new MsgSaveUser(this.address, context.getBackendAddress(), dataSet);
		context.getMessageSystem().sendMessage(msg);
	}

	@Override
	public void findUserByID(long id, String sessionId) {
		Message msg = new MsgGetUserByID(this.address, context.getBackendAddress(), id, sessionId);
		context.getMessageSystem().sendMessage(msg);
	}

	@Override
	public void addChatMessage(ChatMessage message) {
		Message msg = new MsgAddChatMessage(this.address, context.getBackendAddress(), message);
		context.getMessageSystem().sendMessage(msg);
	}

	@Override
	public void informAll(String text) {
		sockets.forEach(sc -> sc.sendMessage(text));
	}

	@Override
	public void register(ChatSocket chatSocket) {
		sockets.add(chatSocket);
		Message msg = new MsgGetAllMessages(this.address, context.getBackendAddress(), chatSocket);
		context.getMessageSystem().sendMessage(msg);
	}

	@Override
	public void returnAllMessages(ChatSocket chatSocket, String messages) {
		chatSocket.sendMessage(messages);
	}

	@Override
	public void register(String sessionId, ListOfUsersSocket listOfUsersSocket) {
		listOfUsersSockets.put(sessionId, listOfUsersSocket);
	}

	@Override
	public void register(String sessionId, FindUserSocket findUserSocket) {
		findUserSockets.put(sessionId, findUserSocket);
	}

	@Override
	public void unregister(String sessionId) {
		listOfUsersSockets.remove(sessionId);
	}


	@Override
	public SecurityDAO getSecurityDao() {
		return securityDao;
	}

	@Override
	public void setSecurityDao(SecurityDAO securityDao) {
		FrontendServiceImpl.securityDao = securityDao;
	}

	@Override
	public FindUserSocket getFindUserServlet(String sessionId) {
		return findUserSockets.get(sessionId);
	}

	@Override
	public void unregister(ChatSocket chatSocket) {
		sockets.remove(chatSocket);
	}

	@Override
	public void getAccounts(String requestId) {
		Message msg = new MsgGetAccounts(this.address, context.getBackendAddress(), requestId);
		context.getMessageSystem().sendMessage(msg);
	}

	@Override
	public Address getAddress() {
		return address;
	}
}
