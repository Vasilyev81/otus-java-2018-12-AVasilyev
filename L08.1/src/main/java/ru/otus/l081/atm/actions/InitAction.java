package ru.otus.l081.atm.actions;

import ru.otus.l081.atm.AtmStates;
import ru.otus.l081.atm.cashdrawer.CashBox;
import ru.otus.l081.userinterface.UserInterface;

public class InitAction implements AbstractAction {
	private final UserInterface uInterface;
	private final CashBox cashDrawer;

	public InitAction(UserInterface uInterface, CashBox cashDrawer) {
		this.uInterface = uInterface;
		this.cashDrawer = cashDrawer;

	}

	@Override
	public AtmStates execute() {
		/*uInterface.print("atm initialization: ");
		for (int i = 0; i < 10; i++) {
			uInterface.print("* ");
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/
		return AtmStates.CURRENCY_CHOICE;
	}
}
