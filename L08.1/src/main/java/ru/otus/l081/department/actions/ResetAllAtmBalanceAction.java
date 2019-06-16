package ru.otus.l081.department.actions;

import ru.otus.l081.department.DepartmentStates;
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
	public DepartmentStates execute() {
		ui.print("\nResetting ALL ATM's to saved backups\n");
		boolean restore = caretaker.restoreAll();
		if (restore) {
			ui.print("All ATM's is restored from backup.\n");
		} else {
			ui.print("Something went wrong!\nGo and kill programmer!\n)))");
		}
		return DepartmentStates.CHOOSE_ACTION;
	}
}
