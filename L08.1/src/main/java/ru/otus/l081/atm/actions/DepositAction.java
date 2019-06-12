package ru.otus.l081.atm.actions;

import ru.otus.l081.atm.Atm;
import ru.otus.l081.atm.transactions.Transaction;
import ru.otus.l081.userinterface.UserInterface;

import java.util.List;

public class DepositAction implements AbstractAction {
	private UserInterface ui;
	private List denominationsList;
	private Transaction transaction;

	public DepositAction(UserInterface ui, Transaction transaction) {
		this.ui = ui;
		this.transaction = transaction;
		this.denominationsList = transaction.getDenominationsList();
	}

	@Override
	public Atm.States execute() {
		ui.print("\nChoose banknotes denomination which you will add from listed below:\n");
		ui.printList(denominationsList);
		ui.print("\nAlso you can enter \"0\" to go back to previous menu.");
		Integer denomination = handleDenominationInput();
		Integer numberOfBanknotes = handleNumberOfBanknotesInput();
		if (denomination == 0 || numberOfBanknotes == 0) {
			return Atm.States.CURRENCY_CHOICE;
		}
		transaction.deposit(denomination, numberOfBanknotes);
		return Atm.States.CURRENCY_BALANCE;
	}

	private Integer handleNumberOfBanknotesInput() {
		boolean isValid = false;
		Integer userChoice = -1;
		Integer goToCurrencyChoice = 0;
		while (!isValid) {
			ui.print("\nInput number of banknotes: ");
			try {
				userChoice = Integer.parseInt(ui.read());
			} catch (NumberFormatException e) {
				ui.print("\nYou input unsupported value, try again!");
			}
			if (userChoice < goToCurrencyChoice)
				ui.print("\nValue that you input is not valid, try again!");
			else isValid = true;
		}
		return userChoice;
	}

	private Integer handleDenominationInput() {
		boolean isValid = false;
		Integer userChoice = -1;
		Integer goToCurrencyChoice = 0;
		while (!isValid) {
			ui.print("\nInput banknote denomination: ");
			try {
				userChoice = Integer.parseInt(ui.read());
			} catch (NumberFormatException e) {
				ui.print("\nYou input unsupported value, try again!");
			}
			if (!denominationsList.contains(userChoice) && userChoice != goToCurrencyChoice)
				ui.print("\nValue that you input is not valid, try again!");
			else isValid = true;
		}
		return userChoice;
	}
}
