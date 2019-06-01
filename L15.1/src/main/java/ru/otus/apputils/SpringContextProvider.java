package ru.otus.apputils;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;

public class SpringContextProvider {
	private static GenericApplicationContext applicationContext;

	private static void createInstance() {
		GenericApplicationContext context = new GenericApplicationContext();
		new XmlBeanDefinitionReader(context).loadBeanDefinitions("SpringBeans.xml");
		context.refresh();
		applicationContext = context;
	}

	public static GenericApplicationContext getContext() {
		if (applicationContext == null) {
			createInstance();
		}
		return applicationContext;
	}
}
