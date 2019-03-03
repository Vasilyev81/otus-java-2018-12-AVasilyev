package ru.otus.L051.testFramework;

import ru.otus.L051.testFramework.annotations.*;

import java.lang.reflect.Method;
import java.util.*;

public class AnnotationFramework {
	public static <T extends Class> void runTests(T clazz) {

		List<Method> testMethods = new LinkedList<>();
		List<Method> beforeAllMethods = new LinkedList<>();
		List<Method> beforeEachMethods = new LinkedList<>();
		List<Method> afterEachMethods = new LinkedList<>();
		List<Method> afterAllMethods = new LinkedList<>();

		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			if (method.isAnnotationPresent(BeforeAll.class)) beforeAllMethods.add(method);
			if (method.isAnnotationPresent(BeforeEach.class)) beforeEachMethods.add(method);
			if (method.isAnnotationPresent(AfterEach.class)) afterEachMethods.add(method);
			if (method.isAnnotationPresent(AfterAll.class)) afterAllMethods.add(method);
			if (method.isAnnotationPresent(Test.class)) testMethods.add(method);
		}
		try {
			ReflectionHelper.callMethodsThrowingExceptions(beforeAllMethods, clazz);
			for (Method testMethod : testMethods) {
				Object instance = ReflectionHelper.instantiate(clazz);
				try {
					ReflectionHelper.callMethodsThrowingExceptions(beforeEachMethods, instance);
					ReflectionHelper.callMethod(testMethod, instance);
				} catch (AFException e) {
					System.err.println(e.getCircumstances());
				}
				ReflectionHelper.callMethodsCatchingExceptions(afterEachMethods, instance);
			}
		} catch (AFException e) {
			System.err.println(e.getCircumstances());
		}
		ReflectionHelper.callMethodsCatchingExceptions(afterAllMethods, clazz);
	}
}