package ru.otus.l081.department.actions;

import ru.otus.l081.atm.AtmInterface;
import ru.otus.l081.department.DepartmentStates;
import ru.otus.l081.userinterface.UserInterface;

import java.util.List;

public class AllAtmBalanceAction implements AbstractAction {
	private final List<AtmInterface> atms;
	private final UserInterface ui;

	public AllAtmBalanceAction(List<AtmInterface> atms, UserInterface departmentInterface) {
		this.atms = atms;
		ui = departmentInterface;
	}

	@Override
	public DepartmentStates execute() {
		StringBuilder balance = new StringBuilder();
		for (int i = 0; i < atms.size(); i++) {
			balance.append("\nATM-")
					.append(i + 1)
					.append(" balance:\n")
					.append(this.atms.get(i).getBalanceAsString())
					.append("\n");
		}
		ui.print(balance.toString());
		return DepartmentStates.CHOOSE_ACTION;
	}
}