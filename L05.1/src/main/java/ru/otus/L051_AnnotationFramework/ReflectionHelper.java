package ru.otus.L051_AnnotationFramework;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.*;
import java.util.Arrays;

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
			if (!isAccessible && (constructor!=null)) constructor.setAccessible(false);
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

	static Object callMethod(@NotNull Method method, @NotNull Object object, Object... args) {
		boolean isAccessible = true;
		boolean isStatic;
		try {
			isStatic = Modifier.isStatic(method.getModifiers());
			isAccessible = method.canAccess((isStatic) ? null : object);
			method.setAccessible(true);
			return method.invoke((isStatic) ? null : object, args);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		} finally {
			if (!isAccessible) {
				method.setAccessible(false);
			}
		}
		return null;
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

