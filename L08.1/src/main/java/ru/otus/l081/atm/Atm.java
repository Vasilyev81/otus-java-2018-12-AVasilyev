package ru.otus.l081.atm;

import ru.otus.l081.atm.transactions.Memento;
import ru.otus.l081.userinterface.UserInterface;


public interface Atm {
	void init();

	void start();

	String getID();

	String getBalanceAsString();

	void hookUI(UserInterface ui);

	UserInterface unhookUI();

	Memento getMemento();
}
