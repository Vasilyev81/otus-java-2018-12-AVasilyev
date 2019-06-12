package ru.otus.l081.atm.actions;

import ru.otus.l081.atm.Atm;

public interface AbstractAction {
	Atm.States execute();
}
