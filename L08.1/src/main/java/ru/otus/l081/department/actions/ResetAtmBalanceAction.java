package ru.otus.l081.department.actions;

import ru.otus.l081.atm.AtmInterface;
import ru.otus.l081.department.Department;
import ru.otus.l081.department.caretaker.Caretaker;
import ru.otus.l081.userinterface.UserInterface;

import java.util.List;

public class ResetAtmBalanceAction implements AbstractAction {
	private final List<AtmInterface> atms;
	private final UserInterface ui;
	private final Caretaker caretaker;

	public ResetAtmBalanceAction(List<AtmInterface> atms, UserInterface departmentInterface, Caretaker caretaker) {
		this.atms = atms;
		ui = departmentInterface;
		this.caretaker = caretaker;
	}

	@Override
	public Department.States execute() {
		int atms = this.atms.size();
		int choise = 0;
		while (choise < 1 || choise > atms) {
			ui.print("Choose AtmMain (1-" + atms + "):\n");
			choise = chooseAtm();
		}
		boolean restore = caretaker.restoreFromBackup(choise);
		if(restore) {ui.print("ATM-" + choise + " state is restored from backup.\n");}
		else {ui.print("Something went wrong!\n");}
		return Department.States.CHOOSE_ACTION;
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
