package ru.otus.l071.atm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CLInterface {
	public String read() {
		InputStream in = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String line = null;
		try {
			line = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return line;
	}

	public void print(Object line) {
		System.out.print(line);
	}
}