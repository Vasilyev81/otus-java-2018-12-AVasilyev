package ru.otus.backend;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.backend.chatService.ChatService;
import ru.otus.backend.chatService.dto.ChatMessage;
import ru.otus.backend.dbServise.DBService;
import ru.otus.backend.dbServise.HibernateUtilities;
import ru.otus.datasets.AccountDataSet;
import ru.otus.datasets.DataSet;
import ru.otus.datasets.UserDataSet;
import ru.otus.messageSystem.Address;
import ru.otus.messageSystem.MessageSystemContext;
import ru.otus.messageSystem.messages.Message;
import ru.otus.messageSystem.messages.toFrontend.*;
import ru.otus.sockets.ChatSocket;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class BackendServiceImpl implements BackendService {
	private final Address address;
	private MessageSystemContext context;
	@Autowired
	private ChatService chatService;
	@Autowired
	private DBService dbService;
	private static final Logger log = Logger.getLogger(BackendServiceImpl.class.getName());

	public BackendServiceImpl(Address address) {
		this.address = address;
	}

	@Override
	public <T extends DataSet> void save(T dataSet) {
		dbService.save(dataSet);
	}

	@Override
	public <T extends DataSet> void save(List<T> dataSets) {
		dbService.save(dataSets);
	}

	@Override
	public String getDBStatus() {
		return dbService.getLocalStatus();
	}

	@Override
	public void read(long id, String sessionId) {
		UserDataSet user = null;
		try {
			user = HibernateUtilities.removeCircularDependencies
					(HibernateUtilities.unproxy(dbService.read(id)));
		} catch (ObjectNotFoundException ex) {
			log.log(Level.ALL, "Exception in DB during reading User by ID: " + id, ex);
		}
		MsgGetUserByIDAnswer msg = new MsgGetUserByIDAnswer(this.address, context.getFrontAddress(), user, sessionId);
		context.getMessageSystem().sendMessage(msg);
	}

	@Override
	public void readAll(String sessionId) {
		List<UserDataSet> result = dbService.readAll();
		List<UserDataSet> users = result.stream().map(HibernateUtilities::unproxy).
				map(HibernateUtilities::removeCircularDependencies).collect(Collectors.toList());
		MsgGetAllUsersAnswer msg = new MsgGetAllUsersAnswer(this.address, context.getFrontAddress(), sessionId, users);
		context.getMessageSystem().sendMessage(msg);
	}

	@Override
	public void readByName(String name, String sessionId) {
		UserDataSet user = dbService.readByName(name);
	}

	@Override
	public void getAccounts(String requestId) {
		List<AccountDataSet> accounts = dbService.getAccounts();
		Message msg = new MsgGetAccountsAnswer(this.address, context.getFrontAddress(), requestId, accounts);
		context.getMessageSystem().sendMessage(msg);
	}

	@Override
	public void addChatMessage(ChatMessage message) {
		chatService.addNewMessage(message);
		context.getMessageSystem().sendMessage(new MsgAddChatMessageAnswer(this.address, context.getFrontAddress(), message.getText()));
	}

	@Override
	public void getAllChatMessages(ChatSocket chatSocket) {
		String allMessages = chatService.getAllMessages();
		context.getMessageSystem().sendMessage(new MsgGetAllMessagesAnswer(this.address, context.getFrontAddress(), chatSocket, allMessages));
	}

	@Override
	public void shutdown() {
		dbService.shutdown();
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
	public Address getAddress() {
		return address;
	}
}













