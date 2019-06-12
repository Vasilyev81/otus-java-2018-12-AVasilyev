package ru.otus.l081.atm.actions;

import ru.otus.l081.atm.Atm;
import ru.otus.l081.userinterface.UserInterface;

public class TransactionChoice implements AbstractAction {
	private UserInterface ui;

	public TransactionChoice(UserInterface ui) {
		this.ui = ui;
	}

	@Override
	public Atm.States execute() {
		StringBuilder sb = new StringBuilder()
				.append("\nYou can deposit or withdraw money.")
				.append("\nTo pass money ->1\n")
				.append("To withdraw money -> 2\n")
				.append("To go back to \"Currency choice\" -> 3\n")
				.append("To get currencies balance -> 4\n")
				.append("To go back to \"AtmDepartment\" -> 5\n");
		ui.print(sb.toString());
		return handleActionChoiceInput();
	}

	private Atm.States handleActionChoiceInput() {
		Atm.States states;
		switch (ui.read()) {
			case ("1"): {
				states = Atm.States.DEPOSIT;
				break;
			}
			case ("2"): {
				states = Atm.States.WITHDRAW;
				break;
			}
			case ("3"): {
				states = Atm.States.CURRENCY_CHOICE;
				break;
			}case ("4"): {
				states = Atm.States.CURRENCY_BALANCE;
				break;
			}case ("5"): {
				states = Atm.States.FINISH_WORK;
				break;
			}
			default: { //TODO> make while(unsupportedValue) to handle wrong input inside this class, without going back to main flow
				ui.print("\nYou input unsupported value,\ntry again!");
				states = Atm.States.ACTION_CHOICE;
			}
		}
		return states;
	}
}
