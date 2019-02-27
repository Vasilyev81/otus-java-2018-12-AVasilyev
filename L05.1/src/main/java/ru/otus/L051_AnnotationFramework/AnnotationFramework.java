package ru.otus.L051_AnnotationFramework;

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
		for (Method beforeAllMethod : beforeAllMethods) {
			ReflectionHelper.callMethod(beforeAllMethod, clazz);
		}
		for (Method testMethod : testMethods) {
			Object instance = ReflectionHelper.instantiate(clazz);
			for (Method beforeEachMethod : beforeEachMethods) {
				ReflectionHelper.callMethod(beforeEachMethod, instance);
			}
			ReflectionHelper.callMethod(testMethod, instance);
			for (Method afterEachMethod : afterEachMethods) {
				ReflectionHelper.callMethod(afterEachMethod, instance);
			}
		}
		for (Method afterAllMethod : afterAllMethods) {
			ReflectionHelper.callMethod(afterAllMethod, clazz);
		}
	}
}