package ru.otus.l081.department;

import ru.otus.l081.atm.AtmManager;
import ru.otus.l081.department.actions.*;
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
		CHOOSE_ACTION,
		WORK_WITH_ATM,
		GET_ATM_BALANCE,
		GET_ALL_ATM_BALANCE,
		RESET_ATM,
		RESET_ALL_ATM,
		STOP
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

	public void start() {
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
				case GET_ATM_BALANCE: {
				state = perform(new AtmBalanceAstion(departmentInterface));
					break;
				}
				case GET_ALL_ATM_BALANCE: {
					state = perform(new AllAtmBalanceAction(departmentInterface));
					break;
				}
				case RESET_ATM: {
					state = perform(new ResetAtmBalanceAction(departmentInterface));
					break;
				}
				case RESET_ALL_ATM: {
					state = perform(new ResetAllAtmBalanceAction(departmentInterface));
					break;
				}
				case STOP: {
					work = false;
					break;
				}
			}
		}
	}

	private States perform(AbstractAction action) {
		return action.execute();
	}
}
