package ru.otus.l081.department;

import ru.otus.l081.atm.AtmManager;
import ru.otus.l081.department.actions.AbstractAction;
import ru.otus.l081.department.actions.ChooseAction;
import ru.otus.l081.department.actions.WorkWithAtmAction;
import ru.otus.l081.userinterface.AtmUserInterface;
import ru.otus.l081.userinterface.UserInterface;

import java.util.ArrayList;
import java.util.List;

public class Department {
	private int atms;
	private UserInterface departmentInterface;
	private List<AtmManager> atmManagers;
	private States state;

	public enum States {
		CHOOSE_ACTION, WORK_WITH_ATM, GET_ATM_BALANCE, GET_ALL_ATMS_BALANCE, RESET_ATM, RESET_ALL_ATMS

	}

	public Department(int atms) {
		this.atms = atms;
		departmentInterface = new AtmUserInterface();
		atmManagers = new ArrayList<>();
		initAtms();
	}

	private void initAtms() {
		if (atmManagers == null) atmManagers = new ArrayList<>();
		for (int i = 0; i < atms; i++) {
			atmManagers.add(new AtmManager());
			atmManagers.get(i).init();
		}
	}

	protected void start() {
		state = States.CHOOSE_ACTION;
		boolean work = true;
		while (work) {
			switch (state) {
				case CHOOSE_ACTION: {
					state = perform(new ChooseAction(departmentInterface));
					break;
				}
				case WORK_WITH_ATM: {
					state = perform(new WorkWithAtmAction(atmManagers, departmentInterface, atms));
					break;
				}



			}
		}
	}

	private States perform(AbstractAction action) {
	return action.execute();
	}
}
