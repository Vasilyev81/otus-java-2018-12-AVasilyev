package ru.otus.backend.dbServise;

import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.otus.datasets.AccountDataSet;
import ru.otus.datasets.DataSet;
import ru.otus.datasets.UserDataSet;

import java.util.List;
import java.util.function.Function;

public class DBServiceImpl implements DBService {
	private final SessionFactory sessionFactory;

	public DBServiceImpl(Configuration configuration) {
		sessionFactory = createSessionFactory(configuration);
	}

	private static SessionFactory createSessionFactory(Configuration configuration) {
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
		builder.applySettings(configuration.getProperties());
		ServiceRegistry serviceRegistry = builder.build();
		return configuration.buildSessionFactory(serviceRegistry);
	}

	@Override
	public String getLocalStatus() {
		return runInTransaction(session -> session.getTransaction().getStatus().name());
	}

	@Override
	public <T extends DataSet> void save(T dataSet) {
		runInTransaction(session -> {
			DataSetDAO dao = new DataSetDAO(session);
			dao.save(dataSet);
			return null;
		});
	}

	@Override
	public <T extends DataSet> void save(List<T> dataSet) {
		runInTransaction(session -> {
			DataSetDAO dao = new DataSetDAO(session);
			for (T ds : dataSet) {
				dao.save(ds);
			}
			return null;
		});
	}

	@Override
	public UserDataSet read(long id) throws ObjectNotFoundException {
		return runInTransaction(session -> {
			DataSetDAO dao = new DataSetDAO(session);
			UserDataSet object = dao.read(id);
			Hibernate.initialize(object);
			return object;
		});
	}

	@Override
	public List<UserDataSet> readAll() {
		return runInTransaction((Session session) -> {
			DataSetDAO dao = new DataSetDAO(session);
			return dao.readAll();
		});
	}

	@Override
	public UserDataSet readByName(String name) {
		return runInTransaction(session -> {
			DataSetDAO dao = new DataSetDAO(session);
			return dao.readByName(name);
		});
	}

	@Override
	public List<AccountDataSet> getAccounts() {
		return runInTransaction(session -> {
			DataSetDAO dao = new DataSetDAO(session);
			return dao.readAccounts();
		});
	}

	private <R> R runInTransaction(Function<Session, R> function) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		R result = function.apply(session);
		transaction.commit();
		return result;
	}

	@Override
	public void shutdown() {
		sessionFactory.close();
	}
}













