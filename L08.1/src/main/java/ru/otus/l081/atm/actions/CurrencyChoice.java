package ru.otus.l081.atm.actions;

import ru.otus.l081.atm.Atm;
import ru.otus.l081.atm.transactions.Transaction;
import ru.otus.l081.userinterface.UserInterface;

import java.util.List;

public class CurrencyChoice implements AbstractAction {
	private UserInterface ui;
	private Transaction transaction;
	private List<String> currenciesNames;

	public CurrencyChoice(UserInterface ui, Transaction transaction) {
		this.ui = ui;
		this.transaction = transaction;
		this.currenciesNames = transaction.getCurrenciesNamesList();
	}

	@Override
	public Atm.States execute() {
		ui.print("You can choose between: ");
			var state = handleCurrencyChoiceInput(ui.read());
		ui.print("Your choice is: " + transaction.getCurrency() + "\n");
		return state;
	}

	private Atm.States handleCurrencyChoiceInput(String choice) {
		String reducedChoice = choice.trim().toUpperCase();
		if (currenciesNames.contains(reducedChoice)) {
			transaction.setCurrency(reducedChoice);
			return Atm.States.ACTION_CHOICE;
		} else return Atm.States.CURRENCY_CHOICE;
	}
}
