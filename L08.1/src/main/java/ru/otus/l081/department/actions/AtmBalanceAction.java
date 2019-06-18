package ru.otus.l081.department.actions;

import ru.otus.l081.atm.Atm;
import ru.otus.l081.department.DepartmentStates;
import ru.otus.l081.userinterface.UserInterface;

import java.util.List;

public class AtmBalanceAction implements AbstractAction {
	private final List<Atm> atms;
	private final UserInterface ui;

	public AtmBalanceAction(List<Atm> atms, UserInterface departmentInterface) {
		this.atms = atms;
		ui = departmentInterface;
	}

	@Override
	public DepartmentStates execute() {
		int atms = this.atms.size();
		int choise = 0;
		while (choise < 1 || choise > atms) {
			ui.print("\nChoose ATM (1-" + atms + "):\n");
			choise = chooseAtmNumber();
		}
		String balance = new StringBuilder()
				.append("ATM-")
				.append(choise)
				.append(" balance:\n")
				.append(this.atms.get(choise - 1).getBalanceAsString())
				.append("\n").toString();
		ui.print(balance);
		return DepartmentStates.CHOOSE_ACTION;
	}

	private int chooseAtmNumber() {
		int choise = 0;
		try {
			choise = Integer.parseInt(ui.read().trim());
		} catch (NumberFormatException ex) {
			ui.print("Something went wrong with your choice, try again!\n");
		}
		return choise;
	}
}