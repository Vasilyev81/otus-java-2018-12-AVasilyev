package ru.otus.l081.atm;

public class AtmMain {
	static AtmImpl atmManager = new AtmImpl();

	public static void main(String[] args) {
		atmManager.initAndStart();
	}
}
