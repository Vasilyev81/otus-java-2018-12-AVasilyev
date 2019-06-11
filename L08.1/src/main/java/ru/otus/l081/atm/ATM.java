package ru.otus.l081.atm;

public class ATM {
	static AtmManager atmManager = new AtmManager();

	public static void main(String[] args) {
		atmManager.initAndStart();
	}
}
