package ru.otus.backend.dbServise;

import org.hibernate.cfg.Configuration;
import ru.otus.datasets.*;

public class H2Configuration extends Configuration {
	public H2Configuration() {
		super();
		this.addAnnotatedClass(DataSet.class);
		this.addAnnotatedClass(UserDataSet.class);
		this.addAnnotatedClass(AccountDataSet.class);
		this.addAnnotatedClass(AddressDataSet.class);
		this.addAnnotatedClass(CompanyDataSet.class);
		this.addAnnotatedClass(PhoneDataSet.class);

		this.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		this.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
		this.setProperty("hibernate.connection.url", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
		this.setProperty("hibernate.connection.pool_size", "10");

		this.setProperty("hibernate.show_sql", "false");
		this.setProperty("hibernate.hbm2ddl.auto", "create");
		this.setProperty("hibernate.connection.useSSL", "false");
		this.setProperty("hibernate.enable_lazy_load_no_trans", "true");
		//this.setProperty("hibernate.connection.autocommit", "false");

	}
}
