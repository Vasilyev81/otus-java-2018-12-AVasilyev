package ru.otus.l072.actions;

import ru.otus.l072.AtmManager;

public interface AbstractAction {
	AtmManager.States execute();
}
