package ru.otus.l081.atm.actions;

import ru.otus.l081.atm.Atm;
import ru.otus.l081.atm.transactions.Transaction;

public class BackupAction implements AbstractAction {

	private Atm.States state;
	private Transaction transaction;

	public BackupAction(Atm.States state, Transaction transaction) {
		this.state = state;
		this.transaction = transaction;
	}

	@Override
	public Atm.States execute() {

		return Atm.States.FINISH_WORK;
	}
}
