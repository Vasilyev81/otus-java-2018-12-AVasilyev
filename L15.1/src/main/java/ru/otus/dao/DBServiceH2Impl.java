package ru.otus.dao;

import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.otus.datasets.*;

import java.util.List;
import java.util.function.Function;

public class DBServiceH2Impl implements DBService {
	private final SessionFactory sessionFactory;

	public DBServiceH2Impl() {
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(DataSet.class);
		configuration.addAnnotatedClass(UserDataSet.class);
		configuration.addAnnotatedClass(AddressDataSet.class);
		configuration.addAnnotatedClass(CompanyDataSet.class);
		configuration.addAnnotatedClass(PhoneDataSet.class);

		configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
		configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
		configuration.setProperty("hibernate.connection.pool_size", "10");

		configuration.setProperty("hibernate.show_sql", "false");
		configuration.setProperty("hibernate.hbm2ddl.auto", "create");
		configuration.setProperty("hibernate.connection.useSSL", "false");
		sessionFactory = createSessionFactory(configuration);
	}

	private static SessionFactory createSessionFactory(Configuration configuration) {
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
		builder.applySettings(configuration.getProperties());
		ServiceRegistry serviceRegistry = builder.build();
		return configuration.buildSessionFactory(serviceRegistry);
	}

	public String getLocalStatus() {
		return runInSession(session -> session.getTransaction().getStatus().name());
	}

	public <T extends DataSet> void save(T dataSet) {
		try (Session session = sessionFactory.openSession()) {
			DataSetDAO dao = new DataSetDAO(session);
			dao.save(dataSet);
			session.save(dataSet);
			session.close();
		}
	}

	public UserDataSet read(long id) throws ObjectNotFoundException {
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













