package ru.otus.l081.atm;

import ru.otus.l081.atm.transactions.AtmMementoInterface;
import ru.otus.l081.userinterface.UserInterface;

public interface AtmInterface {
	void init();
	void start();
	AtmMementoInterface getMemento();
	String getBalance();
	void hookUI(UserInterface ui);
	UserInterface unhookUI();
}
