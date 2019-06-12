package ru.otus.l081.department;

import ru.otus.l081.atm.Atm;
import ru.otus.l081.atm.AtmInterface;
import ru.otus.l081.department.actions.*;
import ru.otus.l081.department.caretaker.Caretaker;
import ru.otus.l081.userinterface.AtmUserInterface;
import ru.otus.l081.userinterface.UserInterface;

import java.util.ArrayList;
import java.util.List;

public class Department {
	private UserInterface departmentInterface;
	private List<AtmInterface> atms;
	private Caretaker caretaker;
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

	public Department(int atmsNumber) {
		departmentInterface = new AtmUserInterface();
		this.atms = new ArrayList<>(atmsNumber);
		caretaker = new Caretaker();
		initAtms(atmsNumber);
	}

	private void initAtms(int atmsNumber) {
		if (atms == null) atms = new ArrayList<>(atmsNumber);
		for (int i = 0; i < atmsNumber; i++) {
			atms.add(new Atm());
			atms.get(i).init();
			caretaker.saveBackup(atms.get(i), i);
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
					state = perform(new WorkWithAtmAction(atms, departmentInterface));
					break;
				}
				case GET_ATM_BALANCE: {
				state = perform(new AtmBalanceAction(atms, departmentInterface));
					break;
				}
				case GET_ALL_ATM_BALANCE: {
					state = perform(new AllAtmBalanceAction(atms, departmentInterface));
					break;
				}
				case RESET_ATM: {
					state = perform(new ResetAtmBalanceAction(atms, departmentInterface, caretaker));
					break;
				}
				case RESET_ALL_ATM: {
					state = perform(new ResetAllAtmBalanceAction(departmentInterface, caretaker));
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
