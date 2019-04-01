package ru.otus.l072.actions;

import ru.otus.l072.AtmManager;
import ru.otus.l072.UserInterface;
import ru.otus.l072.Utils;
import ru.otus.l072.cashdrawer.CashDrawer;

public class InitAction implements AbstractAction {
	private UserInterface uInterface;
	private CashDrawer cashDrawer;
	private Utils utils;

	public InitAction(UserInterface uInterface, CashDrawer cashDrawer, Utils utils) {
		this.uInterface = uInterface;
		this.cashDrawer = cashDrawer;
		this.utils = utils;

	}

	@Override
	public AtmManager.States execute() {
		utils.initCashDrawer(cashDrawer);
		uInterface.print("ATM initialization: ");
		for (int i = 0; i < 10; i++) {
			uInterface.print("* ");
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return AtmManager.States.CURRENCY_CHOICE;
	}
}
