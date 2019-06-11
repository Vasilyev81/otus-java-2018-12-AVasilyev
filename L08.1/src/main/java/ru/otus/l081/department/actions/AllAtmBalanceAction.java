package ru.otus.l081.department.actions;

import ru.otus.l081.department.Department;
import ru.otus.l081.userinterface.UserInterface;

public class AllAtmBalanceAction implements AbstractAction {
	private final UserInterface ui;

	public AllAtmBalanceAction(UserInterface departmentInterface) {
	ui = departmentInterface;
	}

	@Override
	public Department.States execute() {
		return null;
	}
}
