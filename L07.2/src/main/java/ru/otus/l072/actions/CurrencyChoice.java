package ru.otus.l072.actions;

import ru.otus.l072.AtmManager;
import ru.otus.l072.Transaction;
import ru.otus.l072.UserInterface;

import java.util.List;

public class CurrencyChoice implements AbstractAction {
	private UserInterface ui;
	private Transaction transaction;
	List<String> currenciesNames;

	public CurrencyChoice(UserInterface ui, Transaction transaction) {
		this.ui = ui;
		this.transaction = transaction;
		this.currenciesNames = transaction.getCurrenciesNamesList();
	}

	@Override
	public AtmManager.States execute() {
		ui.print("\nYou can choose between: ");
		ui.printList(currenciesNames);
		ui.print("\nInput currency name: ");
		var state = handleCurrencyChoiceInput(ui.read());
		ui.print("\nYour choice is:\n->" + transaction.getCurrency());
		return state;
	}

	private AtmManager.States handleCurrencyChoiceInput(String choice) {
		String reducedChoice = choice.trim().toUpperCase();
		if (currenciesNames.contains(reducedChoice)) {
			transaction.setCurrency(reducedChoice);
			return AtmManager.States.ACTION_CHOICE;
		} else return AtmManager.States.CURRENCY_CHOICE;
	}
}
