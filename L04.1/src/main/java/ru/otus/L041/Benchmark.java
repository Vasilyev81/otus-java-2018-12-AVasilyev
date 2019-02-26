package ru.otus.L041;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Benchmark implements BenchmarkMBean {
	private static int size;
	private final Set<Object[]> storage = new HashSet<>();

	@Override
	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public int getSize() {
		return size;
	}

	void run() throws InterruptedException {
		int counter = 0;
		while (true) {
			final int local = size;
			final Object[] array = new Object[local];
			System.out.println("Array of size: " + array.length + " created.");
			for (int i = 0; i < local; i++) array[i] = new String(new char[0]);
			System.out.println("Created " + local + " objects.");
			counter++;
			if ((counter % 100) == 0) {
				storage.add(array);
			}
			if ((counter % 110) == 0) storage.remove(array);
			Thread.sleep(100);
		}
	}
}