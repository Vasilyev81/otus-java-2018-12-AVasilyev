package ru.otus.l081.atm.actions;

import ru.otus.l081.atm.AtmManager;
import ru.otus.l081.atm.Transaction;

public class BackupAction implements AbstractAction {

	private AtmManager.States state;
	private Transaction transaction;

	public BackupAction(AtmManager.States state, Transaction transaction) {
		this.state = state;
		this.transaction = transaction;
	}

	@Override
	public AtmManager.States execute() {

		return AtmManager.States.FINISH_WORK;
	}
}
