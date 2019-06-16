package ru.otus.l081.department.caretaker;

import ru.otus.l081.atm.AtmInterface;
import ru.otus.l081.atm.transactions.AtmMementoInterface;

import java.util.*;

public class Caretaker {
	private final List<AtmInterface> atms = new ArrayList<>();
	private final Map<String, AtmMementoInterface> mementos;

	public Caretaker() {
		mementos = new HashMap<>();
	}

	public void saveBackup(AtmInterface atm) {
		atms.add(atm);
		atm.saveMemento(mementos);
	}

	public boolean restoreFromBackup(int i) {
		return atms.get(i).selectFromMementos(mementos).restore(mementos);
	}

	public boolean restoreAll() {
		 return atms.stream().map(atmInterface -> atmInterface.selectFromMementos(mementos)).map(atmMementoInterface -> atmMementoInterface.restore(mementos)).noneMatch(b -> (!b));
	}
}
