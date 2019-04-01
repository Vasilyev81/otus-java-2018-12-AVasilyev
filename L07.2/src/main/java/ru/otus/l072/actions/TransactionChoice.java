package ru.otus.l072.actions;

import ru.otus.l072.AtmManager;
import ru.otus.l072.UserInterface;

public class TransactionChoice implements AbstractAction {
	private UserInterface ui;

	public TransactionChoice(UserInterface ui) {
		this.ui = ui;
	}

	@Override
	public AtmManager.States execute() {
		ui.print("\nYou can deposit or withdraw money.");
		ui.print("\nTo pass type \"1\"");
		ui.print("\nTo withdraw type \"2\"");
		ui.print("\nTo go back to currency choice \"3\"");
		ui.print("\nTo get  currencies balance \"4\"");
		ui.print("\nYour choice is:\n->");
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
			}
			default: {
				ui.print("\nYou input unsupported value,\ntry again!");
				states = AtmManager.States.ACTION_CHOICE;
			}
		}
		return states;
	}
}
