package ru.otus.l081.atm.actions;

import ru.otus.l081.atm.AtmStates;
import ru.otus.l081.atm.transactions.Transaction;
import ru.otus.l081.userinterface.UserInterface;

public class PrintBalanceAction implements AbstractAction {
	private final UserInterface ui;
	private final Transaction transaction;

	public PrintBalanceAction(UserInterface ui, Transaction transaction) {
		this.ui = ui;
		this.transaction = transaction;
	}

	@Override
	public AtmStates execute() {
		ui.print(new StringBuilder().append("\nBalance by currency:\n") + transaction.getBalanceAsString());
		return AtmStates.CURRENCY_CHOICE;
	}
}
