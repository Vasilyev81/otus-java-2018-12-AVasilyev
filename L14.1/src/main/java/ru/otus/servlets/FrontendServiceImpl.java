package ru.otus.servlets;

import ru.otus.datasets.*;
import ru.otus.messageSystem.*;
import ru.otus.messageSystem.messages.*;
import ru.otus.messageSystem.messages.toDB.*;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

public class FrontendServiceImpl implements FrontendService {
	private final Address address;
	private final MessageSystemContext context;
	private final static Logger log = Logger.getLogger(FrontendServiceImpl.class.getName());
	LinkedBlockingQueue<List<UserDataSet>> userDataSetList = new LinkedBlockingQueue<>();

	public FrontendServiceImpl(MessageSystemContext context, Address address) {
		this.context = context;
		this.address = address;
	}

	public void init() {
		context.setFrontAddress(this);
	}

	@Override
	public void readAll() {
		Message msg = new MsgGetAllUsers(getAddress(), context.getDbAddress());
		context.getMessageSystem().sendMessage(msg);
	}

	public List<UserDataSet> getResultList() {
		try {
			return userDataSetList.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void setResultList(List<UserDataSet> userDataSetList) {
		try {
			this.userDataSetList.put(userDataSetList);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public <T extends DataSet> void save(T... dataSet) {
		Message msg = new MsgSaveUser(getAddress(), context.getDbAddress(), dataSet);
		context.getMessageSystem().sendMessage(msg);
	}

	@Override
	public void findUserByID(long id) {
		Message msg = new MsgGetUserByID(getAddress(),context.getDbAddress(), id);
		context.getMessageSystem().sendMessage(msg);
	}

	@Override
	public void returnUserResult(UserDataSet dataSet) {
		userDataSetList.add(Collections.singletonList(dataSet));
	}

	@Override
	public Address getAddress() {
		return address;
	}

	@Override
	public MessageSystem getMessageSystem() {
		return context.getMessageSystem();
	}
}
