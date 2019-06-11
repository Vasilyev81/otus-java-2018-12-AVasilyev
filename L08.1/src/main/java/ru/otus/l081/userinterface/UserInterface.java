package ru.otus.l081.userinterface;

import java.util.List;

public interface UserInterface {
	public void print(String message);
	public String read();
	public <E> void printList(List<E> list);
}
