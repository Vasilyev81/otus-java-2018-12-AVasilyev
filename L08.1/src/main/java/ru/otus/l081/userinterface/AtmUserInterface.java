package ru.otus.l081.userinterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class AtmUserInterface implements UserInterface {
	private BufferedReader br;

	public AtmUserInterface() {
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
		print(list.stream().map(Object::toString).collect(Collectors.joining(", ", "", "\n")));
	}
}
