package ru.otus.l081.department.actions;

import ru.otus.l081.department.DepartmentStates;
import ru.otus.l081.userinterface.UserInterface;

import java.util.HashMap;
import java.util.Map;

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
		String choise = userInterface.read().trim().toUpperCase();
		Map<String, DepartmentStates> statesMap = new HashMap<>();
		statesMap.put("1", DepartmentStates.WORK_WITH_ATM);
		statesMap.put("2", DepartmentStates.GET_ATM_BALANCE);
		statesMap.put("3", DepartmentStates.GET_ALL_ATM_BALANCE);
		statesMap.put("4", DepartmentStates.RESET_ATM);
		statesMap.put("5", DepartmentStates.RESET_ALL_ATM);
		statesMap.put("6", DepartmentStates.STOP);
		DepartmentStates result = statesMap.get(choise);
		if(result == null) result = DepartmentStates.CHOOSE_ACTION;
		return result;
	}
}
