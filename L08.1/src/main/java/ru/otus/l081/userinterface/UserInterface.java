package ru.otus.l081.userinterface;

import java.util.List;

public interface UserInterface {
	void print(String message);
	String read();
	<E> void printList(List<E> list);
}
