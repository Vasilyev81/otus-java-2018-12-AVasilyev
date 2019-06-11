package ru.otus.l081.atm.actions;

import ru.otus.l081.atm.AtmManager;
import ru.otus.l081.atm.cashdrawer.CashDrawer;
import ru.otus.l081.userinterface.UserInterface;

import java.util.List;

public class PrintBalanceAction implements AbstractAction {
	private UserInterface ui;
	CashDrawer cashDrawer;

	public PrintBalanceAction(UserInterface ui, CashDrawer cashDrawer) {
		this.ui = ui;
		this.cashDrawer = cashDrawer;
	}
//TODO: It is desirable to correct the output of the balance
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
			sb.append("\n");
			ui.print(sb.toString());
		}
		return AtmManager.States.CURRENCY_CHOICE;
	}
}
