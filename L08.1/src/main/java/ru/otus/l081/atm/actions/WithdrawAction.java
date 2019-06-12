package ru.otus.l081.atm.actions;

import ru.otus.l081.atm.Atm;
import ru.otus.l081.atm.transactions.Transaction;
import ru.otus.l081.atm.transactions.TransactionException;
import ru.otus.l081.userinterface.UserInterface;

public class WithdrawAction implements AbstractAction {
	private UserInterface ui;
	private Transaction transaction;

	public WithdrawAction(UserInterface ui, Transaction transaction) {
		this.ui = ui;
		this.transaction = transaction;
	}

	@Override
	public Atm.States execute() {
		Integer withdrawValue = handleWithdrawInput();
		try {
			String userOutput = transaction.withdraw(withdrawValue);
			ui.print("Take your money: " + userOutput);
		} catch (TransactionException e) {
			e.getMessage();
		}
		return Atm.States.CURRENCY_BALANCE;
	}

	private Integer handleWithdrawInput() {
		Integer result = 0;
		Integer minAvailableBanknote = transaction.getMinAvailableDenomination();
		Integer availableSum = transaction.getAvailableCashForCurrentCurrency();
		ui.print("Withdraw value must be a multiple of " + minAvailableBanknote + " and not more then " + availableSum + "." );
		ui.print("\nYour choice is:\n->");
		boolean isValid = false;
		while (!isValid) {
			try {
				result = Integer.parseInt(ui.read());
			} catch (NumberFormatException e) {
				ui.print("\nInputted result is not a number,\ntry again:");
				continue;
			}
			if (result % minAvailableBanknote != 0) {
				ui.print("\nInputted result is not multiple of: " + minAvailableBanknote + "\ntry again:");
				continue;
			}
			if (result > availableSum) {
				ui.print("\nNot enough money for withdraw the required amount,\ntry entering a lower result:");
				continue;
			}
			isValid = true;
		}
		return result;
	}
}
