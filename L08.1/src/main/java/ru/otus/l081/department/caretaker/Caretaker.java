package ru.otus.l081.department.caretaker;

import ru.otus.l081.atm.AtmInterface;
import ru.otus.l081.atm.transactions.AtmMementoInterface;

import java.util.HashMap;
import java.util.Map;

public class Caretaker {
	private Map<Integer, AtmMementoInterface> mementos;

	public Caretaker() {
		mementos = new HashMap<>();
	}

	public void saveBackup(AtmInterface atm, int i) {
		AtmMementoInterface memento = atm.getMemento();
		mementos.put(i, memento);
	}

	public boolean restoreFromBackup(int i) {
		return mementos.get(i).restore();
	}

	public boolean restoreAll() {
		return mementos.values().stream().map(AtmMementoInterface::restore).noneMatch(b -> (!b));
	}
}
