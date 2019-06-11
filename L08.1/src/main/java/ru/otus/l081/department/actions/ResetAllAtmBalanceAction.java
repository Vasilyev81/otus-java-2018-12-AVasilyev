package ru.otus.l081.department.actions;

import ru.otus.l081.department.Department;
import ru.otus.l081.userinterface.UserInterface;

public class ResetAllAtmBalanceAction implements AbstractAction {
	private final UserInterface ui;

	public ResetAllAtmBalanceAction(UserInterface departmentInterface) {
	ui = departmentInterface;
	}

	@Override
	public Department.States execute() {
		return null;
	}
}
