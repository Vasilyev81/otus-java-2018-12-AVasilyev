package ru.otus.l111.dbservice.hibernate.hiberconfigs;

import org.hibernate.cfg.Configuration;
import ru.otus.l111.datasets.*;

public class MySqlConfiguration extends Configuration {
	public MySqlConfiguration() {
		super();
		this.addAnnotatedClass(DataSet.class);
		this.addAnnotatedClass(UserDataSet.class);
		this.addAnnotatedClass(AddressDataSet.class);
		this.addAnnotatedClass(CompanyDataSet.class);
		this.addAnnotatedClass(PhoneDataSet.class);

		this.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
		this.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
		this.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/jetty_db");
		this.setProperty("hibernate.connection.username", "root");
		this.setProperty("hibernate.connection.password", "e4rt56yhu8");
		this.setProperty("hibernate.connection.pool_size", "10");

		this.setProperty("hibernate.show_sql", "false");
		this.setProperty("hibernate.hbm2ddl.auto", "create");
		this.setProperty("hibernate.connection.useSSL", "false");
		this.setProperty("hibernate.enable_lazy_load_no_trans", "true");
	}
}
