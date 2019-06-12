package ru.otus.l081.department.actions;

import ru.otus.l081.department.Department;
import ru.otus.l081.department.caretaker.Caretaker;
import ru.otus.l081.userinterface.UserInterface;


public class ResetAllAtmBalanceAction implements AbstractAction {
	private final UserInterface ui;
	private final Caretaker caretaker;

	public ResetAllAtmBalanceAction(UserInterface departmentInterface, Caretaker caretaker) {
		ui = departmentInterface;
		this.caretaker = caretaker;
	}

	@Override
	public Department.States execute() {
		ui.print("Resetting ALL ATM's to saved backups\n");
		boolean restore = caretaker.restoreAll();
		if (restore) {
			ui.print("All ATM's is restored from backup.\n");
		} else {
			ui.print("Something went wrong!\nGo and kill programmer!)))");
		}
		return Department.States.CHOOSE_ACTION;
	}
}
