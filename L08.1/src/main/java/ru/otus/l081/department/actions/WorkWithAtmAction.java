package ru.otus.l081.department.actions;

import ru.otus.l081.atm.AtmManager;
import ru.otus.l081.department.Department;
import ru.otus.l081.userinterface.UserInterface;

import java.util.List;

public class WorkWithAtmAction implements AbstractAction {
	private final List<AtmManager> atmManagers;
	private final UserInterface ui;
	private final int atms;

	public WorkWithAtmAction(List<AtmManager> atmManagers, UserInterface departmentInterface, int atms) {
		this.atmManagers = atmManagers;
		ui = departmentInterface;
		this.atms = atms;
	}

	@Override
	public Department.States execute() {
		int choise = 0;
		while (choise < 1 || choise > atms) {
			ui.print("Choose ATM (1-" + atms + "):\n");
			choise = chooseAtm();
		}
		AtmManager atm = atmManagers.get(choise - 1);
		atm.start();
		atm.returnUI();
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
