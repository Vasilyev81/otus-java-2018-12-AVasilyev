package ru.otus.l081.department;

import ru.otus.l081.atm.*;
import ru.otus.l081.department.caretaker.Caretaker;
import ru.otus.l081.userinterface.*;

import java.util.*;

public class Department {
	private UserInterface ui;
	private final List<Atm> atms;
	private final Caretaker caretaker;
	private DepartmentStates state;

	public Department(int atmsNumber) {
		DepartmentUtil.init();
		ui = new UserInterfaceImpl();
		this.atms = new ArrayList<>(atmsNumber);
		caretaker = new Caretaker();
		initAtms(atmsNumber);
	}

	private void initAtms(int atmsNumber) {
		for (int i = 0; i < atmsNumber; i++) {
			Atm atm = new AtmImpl();
			atms.add(atm);
			atms.get(i).init();
			caretaker.saveBackup(atm);
		}
	}

	public void start() {
		state = DepartmentStates.CHOOSE_ACTION;
		boolean work = true;
		while (work) {
			DepartmentStates result = Arrays.stream(DepartmentStates.values()).filter(states -> states == state)
					.map(state -> state.make(ui, atms, caretaker)).findFirst().get().execute();
			if (result == null) work = false;
			else this.state = result;
		}
	}
}
