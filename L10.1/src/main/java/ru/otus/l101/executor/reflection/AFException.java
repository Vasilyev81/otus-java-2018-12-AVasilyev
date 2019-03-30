package ru.otus.l101.executor.reflection;

import java.lang.reflect.Method;

public class AFException extends Exception {
	private final Method method;
	private final Object object;
	private final Object[] arguments;
	private final Throwable exception;

	public AFException(Throwable e, Method method, Object object, Object[] args) {
		this.exception = e;
		this.method = method;
		this.object = object;
		this.arguments = args;
	}

	public Method getMethod() {
		return method;
	}

	public Object getObject() {
		return object;
	}

	public Object[] getArguments() {
		return arguments;
	}

	public Throwable getException() {
		return exception;
	}

	public String getCircumstances() {
		StringBuilder sb = new StringBuilder();
		sb.append("Exception during executing method: ")
				.append(method.getName())
				.append(", catched exception: ")
				.append(exception.getClass().getSimpleName());

		return sb.toString();
	}
}
