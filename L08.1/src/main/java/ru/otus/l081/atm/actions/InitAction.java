package ru.otus.l081.atm.actions;

import ru.otus.l081.atm.Atm;
import ru.otus.l081.atm.Utils;
import ru.otus.l081.atm.cashdrawer.CashDrawer;
import ru.otus.l081.userinterface.UserInterface;

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
	public Atm.States execute() {
		utils.initCashDrawer(cashDrawer);
		/*uInterface.print("atm initialization: ");
		for (int i = 0; i < 10; i++) {
			uInterface.print("* ");
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/
		return Atm.States.CURRENCY_CHOICE;
	}
}
