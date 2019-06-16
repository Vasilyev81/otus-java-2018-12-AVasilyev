package ru.otus.l081.atm.actions;

import ru.otus.l081.atm.AtmStates;
import ru.otus.l081.atm.cashdrawer.Currency;
import ru.otus.l081.atm.transactions.Transaction;
import ru.otus.l081.userinterface.UserInterface;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SelectCurrencyAction implements AbstractAction {
	private final UserInterface ui;
	private final Transaction transaction;

	public SelectCurrencyAction(UserInterface ui, Transaction transaction) {
		this.ui = ui;
		this.transaction = transaction;
	}

	@Override
	public AtmStates execute() {
		ui.print("\nYou can choose between:\n");
		ui.printList(Arrays.stream(Currency.values()).map(Enum::name).collect(Collectors.toList()));
		AtmStates state = handleCurrencyChoiceInput(ui.read());
		ui.print("Your choice is: " + transaction.getCurrentCurrency().name() + "\n");
		return state;
	}

	private AtmStates handleCurrencyChoiceInput(String choice) {
		String reducedChoice = choice.trim().toUpperCase();
		Currency byName = null;
		try {
			byName = Currency.valueOf(reducedChoice);
		} catch (IllegalArgumentException ex) {
			ui.print("Try again!");
		}
		if (byName != null) {
			transaction.setCurrentCurrency(byName);
			return AtmStates.ACTION_CHOICE;
		} else return AtmStates.CURRENCY_CHOICE;
	}
}
