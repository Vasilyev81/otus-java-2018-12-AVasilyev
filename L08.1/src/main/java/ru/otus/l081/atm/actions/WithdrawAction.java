package ru.otus.l081.atm.actions;

import ru.otus.l081.atm.AtmStates;
import ru.otus.l081.atm.transactions.Transaction;
import ru.otus.l081.userinterface.UserInterface;

public class WithdrawAction implements AbstractAction {
	private final UserInterface ui;
	private final Transaction transaction;

	public WithdrawAction(UserInterface ui, Transaction transaction) {
		this.ui = ui;
		this.transaction = transaction;
	}

	@Override
	public AtmStates execute() {
		Integer withdrawValue = handleWithdrawInput();
		String userOutput = transaction.withdraw(withdrawValue);
		ui.print(new StringBuilder().append("Take your money:\n").append(userOutput).toString());
		return AtmStates.CURRENCY_BALANCE;
	}

	private Integer handleWithdrawInput() {
		Integer result = 0;
		Integer minAvailableBanknote = transaction.getMinAvailableDenomination();
		Integer availableSum = transaction.getAvailableCashForCurrentCurrency();
		ui.print(new StringBuilder().append("\nWithdraw value must be a multiple of ").append(minAvailableBanknote).append(" and not more then ").append(availableSum).append(".\n").toString());
		ui.print("Enter the required amount:\n");
		boolean isValid = false;
		while (!isValid) {
			try {
				result = Integer.parseInt(ui.read());
			} catch (NumberFormatException e) {
				ui.print("Inputted result is not a number,\ntry again:\n");
				continue;
			}
			if (result % minAvailableBanknote != 0) {
				ui.print(new StringBuilder().append("Inputted result is not multiple of: ").append(minAvailableBanknote).append("\ntry again:\n").toString());
				continue;
			}
			if (result > availableSum) {
				ui.print("Not enough money for withdraw the required amount,\ntry entering a lower result:\n");
				continue;
			}
			isValid = true;
		}
		return result;
	}
}
