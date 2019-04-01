package ru.otus.l072.actions;

import ru.otus.l072.AtmManager;
import ru.otus.l072.Transaction;
import ru.otus.l072.actions.AbstractAction;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class BackupAction implements AbstractAction {

	private AtmManager.States state;
	private Transaction transaction;

	public BackupAction(AtmManager.States state, Transaction transaction) {
		this.state = state;
		this.transaction = transaction;
	}

	@Override
	public AtmManager.States execute() {
		try {
			FilterOutputStream fos = new FilterOutputStream(new ObjectOutputStream(System.out));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return AtmManager.States.CURRENCY_CHOICE;
	}
}
