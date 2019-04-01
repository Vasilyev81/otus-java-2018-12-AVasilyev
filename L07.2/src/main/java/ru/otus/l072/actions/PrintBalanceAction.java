package ru.otus.l072.actions;

import ru.otus.l072.AtmManager;
import ru.otus.l072.Transaction;
import ru.otus.l072.UserInterface;
import ru.otus.l072.cashdrawer.CashDrawer;

import java.util.List;

public class PrintBalanceAction implements AbstractAction {
	private UserInterface ui;
	CashDrawer cashDrawer;

	public PrintBalanceAction(UserInterface ui, CashDrawer cashDrawer) {
		this.ui = ui;
		this.cashDrawer = cashDrawer;
	}

	@Override
	public AtmManager.States execute() {
		List<String> currNames = cashDrawer.getCurrenciesNamesList();
		for (String currName : currNames) {
			ui.print("Currency: " + currName + ", Total balance: " + cashDrawer.totalSum(currName));
			List<Integer> denominations = cashDrawer.getDenominationsListByCurrencyName(currName);
			List<Integer> numberOfBanknotes = cashDrawer.getNumberOfBanknotesListByCurrencyName(currName);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < denominations.size(); i++) {
				sb.append("\n")
						.append(denominations.get(i))
						.append(" ")
						.append(": ")
						.append(numberOfBanknotes.get(i));
			}
			ui.print(sb.toString());
		}
		return AtmManager.States.CURRENCY_CHOICE;
	}
}
