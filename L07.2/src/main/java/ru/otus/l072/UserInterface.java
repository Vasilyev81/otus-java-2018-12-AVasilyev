package ru.otus.l072;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class UserInterface {
	private BufferedReader br;

	public UserInterface() {
		this.br = new BufferedReader(new InputStreamReader(System.in));
	}

	public void print(String message) {
		System.out.print(message);
	}

	public String read() {
		String result = null;
		try {
			result = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public <E>void printList(List<E> list) {
		print(list.stream().map(Object::toString).collect(Collectors.joining(", ")));
	}
}
