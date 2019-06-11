package ru.otus.l081.department.actions;

import ru.otus.l081.department.Department;
import ru.otus.l081.userinterface.UserInterface;

public class ChooseAction implements AbstractAction {
	private UserInterface userInterface;

	public ChooseAction(UserInterface departmentInterface) {
		userInterface = departmentInterface;
	}

	@Override
	public Department.States execute() {
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

	private Department.States handleUserInput() {
		String choise = userInterface.read().trim().toUpperCase();
		Department.States result;
		switch (choise) {
			case "1": {
				result = Department.States.WORK_WITH_ATM;
				break;
			}
			case "2": {
				result = Department.States.GET_ATM_BALANCE;
				break;
			}
			case "3": {
				result = Department.States.GET_ALL_ATM_BALANCE;
				break;
			}
			case "4": {
				result = Department.States.RESET_ATM;
				break;
			}
			case "5": {
				result = Department.States.RESET_ALL_ATM;
				break;
			}case "6": {
				result = Department.States.STOP;
				break;
			}
			default: {
				result = Department.States.CHOOSE_ACTION;
				break;
			}
		}
		return result;
	}
}
//WORK_WITH_ATM, GET_ATM_BALANCE, GET_ALL_ATM_BALANCE, RESET_ATM, RESET_ALL_ATM
