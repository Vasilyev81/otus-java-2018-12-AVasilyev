package ru.otus.l072;

public class ATM {
	static AtmManager atmManager = new AtmManager();

	public static void main(String[] args) {
		/*
		Thread thread = new Thread(new AtmManager());
		thread.run();
		*/
		atmManager.initAndStart();
	}
}
