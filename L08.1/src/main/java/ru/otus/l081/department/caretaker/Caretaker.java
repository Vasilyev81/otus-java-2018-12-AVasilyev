package ru.otus.l081.department.caretaker;

import ru.otus.l081.atm.Atm;
import ru.otus.l081.atm.transactions.Memento;

import java.util.*;

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

	public boolean restoreFromBackup(int i) {
		String ID = atms.get(i).getID();
		return mementos.get(ID).restore();
	}

	public boolean restoreAll() {
		 return atms.stream().map(Atm::getID).map(mementos::get).map(Memento::restore).noneMatch(b -> (!b));
	}
}
