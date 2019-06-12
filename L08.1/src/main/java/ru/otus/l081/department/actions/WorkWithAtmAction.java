package ru.otus.l081.department.actions;

import ru.otus.l081.atm.AtmInterface;
import ru.otus.l081.department.Department;
import ru.otus.l081.userinterface.UserInterface;

import java.util.List;

public class WorkWithAtmAction implements AbstractAction {
	private final List<AtmInterface> atms;
	private final UserInterface ui;


	public WorkWithAtmAction(List<AtmInterface> atms, UserInterface departmentInterface) {
		this.atms = atms;
		ui = departmentInterface;
	}

	@Override
	public Department.States execute() {
		int atms = this.atms.size();
		int choise = 0;
		while (choise < 1 || choise > atms) {
			ui.print("Choose ATM (1-" + atms + "):\n");
			choise = chooseAtm();
		}
		AtmInterface atm = this.atms.get(choise - 1);
		atm.hookUI(ui);
		atm.start();
		atm.unhookUI();
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
