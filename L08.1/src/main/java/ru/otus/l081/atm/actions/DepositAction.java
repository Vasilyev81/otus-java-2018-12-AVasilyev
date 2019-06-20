package ru.otus.l081.atm.actions;

import ru.otus.l081.atm.AtmStates;
import ru.otus.l081.atm.transactions.Transaction;
import ru.otus.l081.userinterface.UserInterface;

import java.util.List;

public class DepositAction implements AbstractAction {
	private final UserInterface ui;
	private final List denominationsList;
	private final Transaction transaction;

	public DepositAction(UserInterface ui, Transaction transaction) {
		this.ui = ui;
		this.transaction = transaction;
		this.denominationsList = transaction.getDenominationsList();
	}

	@Override
	public AtmStates execute() {
		ui.print("\nChoose banknote nominal which you will add, from listed below:\n");
		ui.printList(denominationsList);
		ui.print("Also you can enter \"0\" to go back to previous menu.\n");
		int denomination = handleDenominationInput();
		int numberOfBanknotes = handleNumberOfBanknotesInput();
		if (denomination == 0 || numberOfBanknotes == 0) {
			return AtmStates.CURRENCY_CHOICE;
		}
		transaction.deposit(denomination, numberOfBanknotes);
		return AtmStates.CURRENCY_BALANCE;
	}

	private int handleDenominationInput() {
		boolean isValid = false;
		int userChoice = -1;
		int goToCurrencyChoice = 0;
		while (!isValid) {
			ui.print("Input banknote denomination:\n");
			try {
				userChoice = Integer.parseInt(ui.read());
			} catch (NumberFormatException e) {
				ui.print("You input unsupported value, try again!\n");
			}
			if (!denominationsList.contains(userChoice) && userChoice != goToCurrencyChoice)
				ui.print("Value that you input is not valid, try again!\n");
			else isValid = true;
		}
		return userChoice;
	}

	private int handleNumberOfBanknotesInput() {
		boolean isValid = false;
		int userChoice = -1;
		int goToCurrencyChoice = 0;
		while (!isValid) {
			ui.print("Input number of banknotes:\n");
			try {
				userChoice = Integer.parseInt(ui.read());
			} catch (NumberFormatException e) {
				ui.print("You input unsupported value, try again!\n");
			}
			if (userChoice < goToCurrencyChoice)
				ui.print("Value that you input is not valid, try again!\n");
			else isValid = true;
		}
		return userChoice;
	}
}
