package ru.otus.l081.department.actions;

import ru.otus.l081.department.*;
import ru.otus.l081.userinterface.UserInterface;

public class ChooseAction implements AbstractAction {
	private UserInterface userInterface;

	public ChooseAction(UserInterface departmentInterface) {
		userInterface = departmentInterface;
	}

	@Override
	public DepartmentStates execute() {
		StringBuilder sb = new StringBuilder()
				.append("Choose what you want to do:\n")
				.append("Work with ATM  -> 1\n")
				.append("Get balance of one of ATM's -> 2\n")
				.append("Get balances of ALL ATM's -> 3\n")
				.append("Reset one of ATM's -> 4\n")
				.append("Reset All ATM's -> 5\n")
				.append("Stop department -> 6\n");
		userInterface.print(sb.toString());
		return handleUserInput();
	}

	private DepartmentStates handleUserInput() {
		int choice = 0;
		try {
			choice = Integer.parseInt(userInterface.read().trim());
		} catch (NumberFormatException ex) {
			userInterface.print("You input unsupported symbols!\n");
		}
		DepartmentStates result = DepartmentUtil.getStatesMap().get(choice);
		if (result == null) {
			{
				userInterface.print("You input unsupported value!\n");
				result = DepartmentStates.CHOOSE_ACTION;
			}
		}
		return result;
	}
}