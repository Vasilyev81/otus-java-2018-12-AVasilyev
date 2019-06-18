package ru.otus.l081.department;

import ru.otus.l081.department.actions.*;
import ru.otus.l081.department.caretaker.Caretaker;
import ru.otus.l081.userinterface.UserInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum DepartmentStates {
	CHOOSE_ACTION {
		@Override
		AbstractAction make(UserInterface ui, List atms, Caretaker caretaker) {
			return new ChooseAction(ui);
		}
	},
	WORK_WITH_ATM {
		AbstractAction make(UserInterface ui, List atms, Caretaker caretaker) {
			return new WorkWithAtmAction(atms, ui);
		}
	},
	GET_ATM_BALANCE {
		@Override
		AbstractAction make(UserInterface ui, List atms, Caretaker caretaker) {
			return new AtmBalanceAction(atms, ui);
		}
	},
	GET_ALL_ATM_BALANCE {
		@Override
		AbstractAction make(UserInterface ui, List atms, Caretaker caretaker) {
			return new AllAtmBalanceAction(atms, ui);
		}
	},
	RESET_ATM {
		@Override
		AbstractAction make(UserInterface ui, List atms, Caretaker caretaker) {
			return new ResetAtmBalanceAction(atms, ui, caretaker);
		}
	},
	RESET_ALL_ATM {
		@Override
		AbstractAction make(UserInterface ui, List atms, Caretaker caretaker) {
			return new ResetAllAtmBalanceAction(ui, caretaker);
		}
	},
	STOP {
		@Override
		AbstractAction make(UserInterface ui, List atms, Caretaker caretaker) {
			return new StopAction(ui);
		}
	};
	abstract AbstractAction make(UserInterface ui, List atms, Caretaker caretaker);
}
