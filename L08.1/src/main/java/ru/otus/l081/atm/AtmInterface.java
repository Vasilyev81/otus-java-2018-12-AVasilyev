package ru.otus.l081.atm;

import ru.otus.l081.atm.transactions.AtmMementoInterface;
import ru.otus.l081.userinterface.UserInterface;

import java.util.Map;

public interface AtmInterface {
	void init();

	void start();

	void saveMemento(Map<String, AtmMementoInterface> mementos);

	AtmMementoInterface selectFromMementos(Map<String, AtmMementoInterface> mementos);

	String getBalanceAsString();

	void hookUI(UserInterface ui);

	UserInterface unhookUI();
}
