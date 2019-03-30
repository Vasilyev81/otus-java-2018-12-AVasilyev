package ru.otus.l101.executor.reflection;

import com.sun.istack.NotNull;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings({"SameParameterValue", "BooleanVariableAlwaysNegated"})
public final class ReflectionHelper {
	private ReflectionHelper() {
	}

	 public static <T> T instantiate(Class<T> type, Object... args) {
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
			if (!isAccessible) constructor.setAccessible(false);
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

	public static <T> T instantiateByFieldsNames(Class<T> type, List<String> fielsNames, List<Object> values) {
		boolean isAccessible = true;
		Constructor<T> constructor = null;
		Object instance = null;
		try {
			constructor = type.getDeclaredConstructor();
			isAccessible = constructor.canAccess(null);
			constructor.setAccessible(true);
			instance = constructor.newInstance();
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			e.printStackTrace();
		} finally {
			if (!isAccessible) constructor.setAccessible(false);
		}
		System.out.println("");
		for(int index = 0; index < fielsNames.size(); index++){
			setFieldValue(instance, fielsNames.get(index), values.get(index));
		}
		return (T) instance;
	}

	public static Object getFieldValue(Object object, String name) {
		Field field = null;
		boolean isAccessible = true;
		try {
			field = object.getClass().getDeclaredField(name);//TODO: Maybe change to findField?
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

	public static void setFieldValue(Object object, String name, Object value) {
		Field field = null;
		boolean isAccessible = true;
		try {
			field = findField(object, name);
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

	public static Field findField(Object object, String name) throws NoSuchFieldException {
		Field field;
		var clazz = object.getClass();
		try {
			field = clazz.getField(name);
		} catch (NoSuchFieldException e) {
			try {
				field = clazz.getDeclaredField(name);
			} catch (NoSuchFieldException ex) {
				field = findNotPublicField(object.getClass(), name);
			}
		}
		return field;
	}

	public static Field findNotPublicField(Class clazz, String name) throws NoSuchFieldException {
		if (clazz == null) throw new NoSuchFieldException();
		var superClazz = clazz.getSuperclass();
		Field field = null;
		try {
			field = superClazz.getDeclaredField(name);
		} catch (NoSuchFieldException e) {
			System.err.println("Field not found.");
		}
		if (field == null) field = findNotPublicField(superClazz, name);
		return field;
	}

	public static Object callMethodByName(Object object, String name, Object... args) {
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

	public static Object callMethod(Method method, Object object, Object... args) throws AFException {
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

	public static void callMethodsThrowingExceptions(@NotNull List<? extends Method> methods, @NotNull Object object) throws
			AFException {
		for (Method method : methods) {
			callMethod(method, object);
		}
	}

	public static void callMethodsCatchingExceptions(@NotNull List<? extends Method> methods, @NotNull Object object) {
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
	}

	public static <T> List<String> getAllFieldsNames(Class<T> clazz) {
		List<String> allFields = Stream.of(clazz.getFields()).map(Field::getName).collect(Collectors.toList());
		Class superClazz = clazz;
		while (superClazz != Object.class) {
			allFields.addAll(Stream.of(superClazz.getDeclaredFields()).map(Field::getName).collect(Collectors.toList()));
			superClazz = superClazz.getSuperclass();
		}
		return allFields;
	}
}




