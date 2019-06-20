package ru.otus.dao;

import ru.otus.messageSystem.MessageSystemContext;
import ru.otus.datasets.*;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.otus.messageSystem.*;

import java.util.List;
import java.util.function.Function;

public class DBServiceImpl implements DBService {
	private final SessionFactory sessionFactory;
	private final Address address;
	private final MessageSystemContext context;

	public DBServiceImpl(Configuration configuration, MessageSystemContext context, Address address) {
		sessionFactory = createSessionFactory(configuration);
		this.context = context;
		this.address = address;
		init();
	}

	@Override
	public void init() {
		context.getMessageSystem().addAddressee(this);
	}

	@Override
	public Address getAddress() {
		return address;
	}

	@Override
	public MessageSystem getMessageSystem() {
		return context.getMessageSystem();
	}

	private static SessionFactory createSessionFactory(Configuration configuration) {
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
		builder.applySettings(configuration.getProperties());
		ServiceRegistry serviceRegistry = builder.build();
		return configuration.buildSessionFactory(serviceRegistry);
	}

	@Override
	public String getLocalStatus() {
		return runInSession(session -> session.getTransaction().getStatus().name());
	}

	@Override
	public <T extends DataSet> void save(List<T> dataSet) {
		runInSession(session -> {
			DataSetDAO dao = new DataSetDAO(session);
			for (T ds : dataSet) {
				dao.save(ds);
			}
			return null;
		});
	}

	@Override
	public <T extends DataSet> void save(T dataSet) {
		runInSession(session -> {
			DataSetDAO dao = new DataSetDAO(session);
				dao.save(dataSet);
			return null;
		});
	}

	@Override
	public UserDataSet read(long id) {
		return runInSession(session -> {
			DataSetDAO dao = new DataSetDAO(session);
			UserDataSet object = dao.read(id);
			Hibernate.initialize(object);
			return object;
		});
	}

	@Override
	public void shutdown() {
		sessionFactory.close();
	}

	@Override
	public List<UserDataSet> readAll() {
		return runInSession((Session session) -> {
			DataSetDAO dao = new DataSetDAO(session);
			return dao.readAll();
		});
	}

	@Override
	public UserDataSet readByName(String name) {
		return runInSession(session -> {
			DataSetDAO dao = new DataSetDAO(session);
			return dao.readByName(name);
		});
	}

	private <R> R runInSession(Function<Session, R> function) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		R result = function.apply(session);
		transaction.commit();
		return result;
	}
}













