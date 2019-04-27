package ru.otus.dao;

import ru.otus.datasets.*;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.List;
import java.util.function.Function;

public class DBServiceImpl implements DBService {
	private final SessionFactory sessionFactory;

	public DBServiceImpl() {
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(DataSet.class);
		configuration.addAnnotatedClass(UserDataSet.class);
		configuration.addAnnotatedClass(AddressDataSet.class);
		configuration.addAnnotatedClass(CompanyDataSet.class);
		configuration.addAnnotatedClass(PhoneDataSet.class);

		//TODO: Err: Connection leak detected: there are 3 unclosed connections upon shutting down pool jdbc:mysql://localhost:3306/jetty_db
		configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
		configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
		configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/jetty_db");
		configuration.setProperty("hibernate.connection.username", "root");
		configuration.setProperty("hibernate.connection.password", "e4rt56yhu8");
		//configuration.setProperty("hibernate.connection.username", "jetty_admin");
		//configuration.setProperty("hibernate.connection.password", "password");
		configuration.setProperty("hibernate.connection.pool_size", "10");

		configuration.setProperty("hibernate.show_sql", "false");
		configuration.setProperty("hibernate.hbm2ddl.auto", "create");
		configuration.setProperty("hibernate.connection.useSSL", "false");
		configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");

		sessionFactory = createSessionFactory(configuration);
	}

	public DBServiceImpl(Configuration configuration) {
		sessionFactory = createSessionFactory(configuration);
	}

	private static SessionFactory createSessionFactory(Configuration configuration) {
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
		builder.applySettings(configuration.getProperties());
		ServiceRegistry serviceRegistry = builder.build();
		return configuration.buildSessionFactory(serviceRegistry);
	}

	public String getLocalStatus() {
		return runInSession(session -> {
			return session.getTransaction().getStatus().name();
		});
	}

	public <T extends DataSet> void save(T dataSet) {
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













