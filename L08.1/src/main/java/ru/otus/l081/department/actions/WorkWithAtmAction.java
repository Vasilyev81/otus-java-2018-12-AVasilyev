package ru.otus.l081.department.actions;

import ru.otus.l081.atm.AtmInterface;
import ru.otus.l081.department.DepartmentStates;
import ru.otus.l081.userinterface.UserInterface;

import java.util.List;

public class WorkWithAtmAction implements AbstractAction {
	private final UserInterface ui;
	private final List<AtmInterface> atms;

	public WorkWithAtmAction(List<AtmInterface> atms, UserInterface departmentInterface) {
		ui = departmentInterface;
		this.atms = atms;
	}

	@Override
	public DepartmentStates execute() {
		int atms = this.atms.size();
		int choise = 0;
		while (choise < 1 || choise > atms) {
			ui.print("\nChoose ATM (1-" + atms + "):\n");
			choise = chooseAtm();
		}
		AtmInterface atm = this.atms.get(choise - 1);
		atm.hookUI(ui);
		atm.start();
		atm.unhookUI();
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
