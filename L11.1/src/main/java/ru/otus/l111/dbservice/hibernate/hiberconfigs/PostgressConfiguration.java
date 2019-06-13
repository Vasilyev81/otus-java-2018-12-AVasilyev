package ru.otus.l111.dbservice.hibernate.hiberconfigs;

import org.hibernate.cfg.Configuration;
import ru.otus.l111.datasets.AddressDataSet;
import ru.otus.l111.datasets.CompanyDataSet;
import ru.otus.l111.datasets.PhoneDataSet;
import ru.otus.l111.datasets.UserDataSet;

public class PostgressConfiguration extends Configuration {
	public PostgressConfiguration() {
		super();
		this.addAnnotatedClass(UserDataSet.class);
		this.addAnnotatedClass(PhoneDataSet.class);
		this.addAnnotatedClass(AddressDataSet.class);
		this.addAnnotatedClass(CompanyDataSet.class);
		//this.addAnnotatedClass(EmptyDataSet.class);

		this.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");
		this.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
		this.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/hibernate");
		this.setProperty("hibernate.connection.username", "postgres");
		this.setProperty("hibernate.connection.password", "postgres");
		this.setProperty("hibernate.show_sql", "true");
		this.setProperty("hibernate.hbm2dll.auto", "create");
		this.setProperty("hibernate.connection.useSSL", "false");
		//this.setProperty("hibernate.enable_lazy_load_no_trans", "true");
	}
}
