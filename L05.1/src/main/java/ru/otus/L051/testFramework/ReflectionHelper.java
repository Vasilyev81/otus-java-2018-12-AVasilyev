package ru.otus.L051.testFramework;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings({"SameParameterValue", "BooleanVariableAlwaysNegated"})
public final class ReflectionHelper {
	private ReflectionHelper() {
	}

	static <T> T instantiate(Class<T> type, Object... args) {
		boolean isAccessible = true;
		Constructor<T> constructor = null;
		if (args.length == 0) try {
			constructor = type.getDeclaredConstructor();
			isAccessible = constructor.canAccess(null);
			constructor.setAccessible(true);
			return constructor.newInstance();
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			e.printStackTrace();
		} finally {
			if (!isAccessible && (constructor != null)) constructor.setAccessible(false);
		}
		else try {
			final Class<?>[] classes = toClasses(args);
			constructor = type.getDeclaredConstructor(classes);
			return constructor.newInstance(args);
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
				InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	static Object getFieldValue(Object object, String name) {
		Field field = null;
		boolean isAccessible = true;
		try {
			field = object.getClass().getDeclaredField(name); //getField() for public fields
			isAccessible = field.canAccess(object);
			field.setAccessible(true);
			return field.get(object);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			if (field != null && !isAccessible) {
				field.setAccessible(false);
			}
		}
		return null;
	}

	static void setFieldValue(Object object, String name, Object value) {
		Field field = null;
		boolean isAccessible = true;
		try {
			field = object.getClass().getDeclaredField(name); //getField() for public fields
			isAccessible = field.canAccess(object);
			field.setAccessible(true);
			field.set(object, value);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			if (field != null && !isAccessible) {
				field.setAccessible(false);
			}
		}
	}

	static Object callMethodByName(Object object, String name, Object... args) {
		Method method = null;
		boolean isAccessible = true;
		try {
			method = object.getClass().getDeclaredMethod(name, toClasses(args));
			isAccessible = method.canAccess(object);
			method.setAccessible(true);
			return method.invoke(object, args);
		} catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			e.printStackTrace();
		} finally {
			if (method != null && !isAccessible) {
				method.setAccessible(false);
			}
		}
		return null;
	}

	static Object callMethod(@NotNull Method method, @NotNull Object object, Object... args) throws AFException {
		try {
			Object result;
			boolean isAccessible;
			boolean isStatic;
			isStatic = Modifier.isStatic(method.getModifiers());
			isAccessible = method.canAccess((isStatic) ? null : object);
			method.setAccessible(true);
			result = method.invoke((isStatic) ? null : object, args);
			if (!isAccessible) method.setAccessible(false);
			return result;
		} catch (InvocationTargetException | IllegalAccessException e) {
			throw new AFException(e, method, object, args);
		}
	}

	static void callMethodsThrowingExceptions(@NotNull List<? extends Method> methods, @NotNull Object object) throws AFException {
		for (Method method : methods) {
			callMethod(method, object);
		}
	}

	static void callMethodsCatchingExceptions(@NotNull List<? extends Method> methods, @NotNull Object object) {
		for (Method method : methods) {
			try {
				callMethod(method, object);
			} catch (AFException e) {
				System.err.println(e.getCircumstances());
			}
		}
	}

	private static Class<?>[] toClasses(Object[] args) {
		return Arrays.stream(args)
				.map(Object::getClass)
				.toArray(Class<?>[]::new);
//        final Class<?>[] classes = new Class<?>[args.length];
//        for (int i = 0; i < args.length; i++) {
//            classes[i] = args[i].getClass();
//        }
//        return classes;
	}
}

