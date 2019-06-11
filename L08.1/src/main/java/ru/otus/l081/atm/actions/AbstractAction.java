package ru.otus.l081.atm.actions;

import ru.otus.l081.atm.AtmManager;

public interface AbstractAction {
	AtmManager.States execute();
}
