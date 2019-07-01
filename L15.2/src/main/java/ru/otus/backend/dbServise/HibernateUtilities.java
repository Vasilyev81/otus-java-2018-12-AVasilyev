package ru.otus.backend.dbServise;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import ru.otus.datasets.UserDataSet;

public class HibernateUtilities {
	public static <T> T unproxy(T proxy) {
		if (proxy == null) {
			return null;
		}

		if (proxy instanceof HibernateProxy) {
			Hibernate.initialize(proxy);
			HibernateProxy hibernateProxy = (HibernateProxy) proxy;
			T unProxiedObject = (T) hibernateProxy.getHibernateLazyInitializer().getImplementation();
			return unProxiedObject;
		}
		return proxy;
	}

	public static UserDataSet removeCircularDependencies(UserDataSet userDataSet) {
		userDataSet.getCompany().removeEmployees();
		return userDataSet;
	}
}