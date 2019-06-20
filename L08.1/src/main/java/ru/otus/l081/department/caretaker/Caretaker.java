package ru.otus.l081.department.caretaker;

import ru.otus.l081.atm.Atm;
import ru.otus.l081.atm.transactions.Memento;

import java.util.*;
import java.util.stream.IntStream;

public class Caretaker {
	private final List<Atm> atms = new ArrayList<>();
	private final Map<String, Memento> mementos;

	public Caretaker() {
		mementos = new HashMap<>();
	}

	public void saveBackup(Atm atm) {
		atms.add(atm);
		String ID = atm.getID();
		Memento memento = atm.getMemento();
		mementos.put(ID, memento);

	}

	public boolean restoreFromBackup(int index) {
		Atm atm = atms.get(index);
		Memento memento = mementos.get(atm.getID());
		return atm.restoreFromMemento(memento);
	}

	public boolean restoreAll() {
		return IntStream.range(0, atms.size()).mapToObj(this::restoreFromBackup).noneMatch(b -> (!b));
	}
}
