package ru.otus.l081.department.actions;

import ru.otus.l081.atm.Atm;
import ru.otus.l081.department.DepartmentStates;
import ru.otus.l081.department.caretaker.Caretaker;
import ru.otus.l081.userinterface.UserInterface;

import java.util.List;

public class ResetAtmBalanceAction implements AbstractAction {
	private final List<Atm> atms;
	private final UserInterface ui;
	private final Caretaker caretaker;

	public ResetAtmBalanceAction(List<Atm> atms, UserInterface departmentInterface, Caretaker caretaker) {
		this.atms = atms;
		ui = departmentInterface;
		this.caretaker = caretaker;
	}

	@Override
	public DepartmentStates execute() {
		int atms = this.atms.size();
		int choise = 0;
		while (choise < 1 || choise > atms) {
			ui.print("\nChoose Atm (1-" + atms + "):\n");
			choise = chooseAtm();
		}
		boolean restore = caretaker.restoreFromBackup(choise - 1);
		if(restore) {ui.print("ATM-" + choise + " state is restored from backup.\n");}
		else {ui.print("Something went wrong!\n");}
		return DepartmentStates.CHOOSE_ACTION;
	}

	private int chooseAtm() {
		int choise = 0;
		try {
			choise = Integer.parseInt(ui.read().trim());
		} catch (NumberFormatException ex) {
			ui.print("Something went wrong with your choice, try again!\n");
		}
		return choise;
	}
}
