package ru.otus.l111.dbservice.hibernate;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.otus.l111.datasets.DataSet;
import ru.otus.l111.datasets.UserDataSet;
import ru.otus.l111.dbservice.DBService;

import java.util.List;
import java.util.function.Function;

public class DBServiceHibernateImpl implements DBService {
	private final SessionFactory sessionFactory;

	public DBServiceHibernateImpl(Configuration configuration) {
		sessionFactory = createSessionFactory(configuration);
	}

	private static SessionFactory createSessionFactory(Configuration configuration) {
		StandardServiceRegistryBuilder builder =  new StandardServiceRegistryBuilder();
		builder.applySettings(configuration.getProperties());
		ServiceRegistry serviceRegistry = builder.build();
		return configuration.buildSessionFactory(serviceRegistry);
	}
	public String getLocalStatus() {
		return runInSession(session -> {
			return session.getTransaction().getStatus().name();
		});
	}

	public void save(DataSet dataSet) {
		try (Session session = sessionFactory.openSession()) {
			DataSetDAO dao = new DataSetDAO(session);
			dao.save(dataSet);
			session.save(dataSet);
			session.close();
		}
	}

	public UserDataSet read(long id) {
		return runInSession(session -> {
			DataSetDAO dao = new DataSetDAO(session);
			UserDataSet object = dao.read(id);
			Hibernate.initialize(object);
			return object;
		});
	}

	public void shutdown() {
		sessionFactory.close();
	}

	public List<UserDataSet> readAll() {
		return runInSession((Session session) -> {
			DataSetDAO dao = new DataSetDAO(session);
			return dao.readAll();
		});
	}

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
