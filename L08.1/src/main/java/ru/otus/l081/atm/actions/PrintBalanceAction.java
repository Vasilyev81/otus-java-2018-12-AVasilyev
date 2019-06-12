package ru.otus.l081.atm.actions;

import ru.otus.l081.atm.Atm;
import ru.otus.l081.atm.cashdrawer.CashDrawer;
import ru.otus.l081.userinterface.UserInterface;

public class PrintBalanceAction implements AbstractAction {
	private UserInterface ui;
	CashDrawer cashDrawer;

	public PrintBalanceAction(UserInterface ui, CashDrawer cashDrawer) {
		this.ui = ui;
		this.cashDrawer = cashDrawer;
	}

	@Override
	public Atm.States execute() {
		ui.print(cashDrawer.getBalance());
		return Atm.States.CURRENCY_CHOICE;
	}
}
