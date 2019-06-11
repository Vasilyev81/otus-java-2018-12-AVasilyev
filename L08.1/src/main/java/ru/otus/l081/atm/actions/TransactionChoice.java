package ru.otus.l081.atm.actions;

import ru.otus.l081.atm.AtmManager;
import ru.otus.l081.userinterface.UserInterface;

public class TransactionChoice implements AbstractAction {
	private UserInterface ui;

	public TransactionChoice(UserInterface ui) {
		this.ui = ui;
	}

	@Override
	public AtmManager.States execute() {
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

	private AtmManager.States handleActionChoiceInput() {
		AtmManager.States states;
		switch (ui.read()) {
			case ("1"): {
				states = AtmManager.States.DEPOSIT;
				break;
			}
			case ("2"): {
				states = AtmManager.States.WITHDRAW;
				break;
			}
			case ("3"): {
				states = AtmManager.States.CURRENCY_CHOICE;
				break;
			}case ("4"): {
				states = AtmManager.States.CURRENCY_BALANCE;
				break;
			}case ("5"): {
				states = AtmManager.States.FINISH_WORK;
				break;
			}
			default: {
				ui.print("\nYou input unsupported value,\ntry again!");
				states = AtmManager.States.ACTION_CHOICE;
			}
		}
		return states;
	}
}
