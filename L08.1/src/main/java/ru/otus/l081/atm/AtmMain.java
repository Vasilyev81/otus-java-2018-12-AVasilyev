package ru.otus.l081.atm;

public class AtmMain {
	static Atm atmManager = new Atm();

	public static void main(String[] args) {
		atmManager.initAndStart();
	}
}
