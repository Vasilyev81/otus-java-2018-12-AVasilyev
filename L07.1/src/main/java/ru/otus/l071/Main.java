package ru.otus.l071;

import ru.otus.l071.atm.ATMStatesLogic;
import ru.otus.l071.atm.ATMTransactions;
import ru.otus.l071.atm.CLInterface;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		ATMTransactions availableCash = new ATMTransactions();
		CLInterface clInterface = new CLInterface();
		Runnable atmState = new ATMStatesLogic(availableCash, clInterface);
		Thread stateThread = new Thread(atmState);
		stateThread.start();
	}
}
