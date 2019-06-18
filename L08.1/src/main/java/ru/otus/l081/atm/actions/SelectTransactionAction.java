package ru.otus.l081.atm.actions;

import ru.otus.l081.atm.AtmStates;
import ru.otus.l081.atm.AtmUtil;
import ru.otus.l081.userinterface.UserInterface;

import java.util.HashMap;
import java.util.Map;

public class SelectTransactionAction implements AbstractAction {
	private final UserInterface ui;

	public SelectTransactionAction(UserInterface ui) {
		this.ui = ui;
	}

	@Override
	public AtmStates execute() {
		StringBuilder sb = new StringBuilder()
				.append("\nYou can deposit or withdraw money.\n")
				.append("To deposit money ->1\n")
				.append("To withdraw money -> 2\n")
				.append("To go back to \"Currency choice\" -> 3\n")
				.append("To get currencies balance -> 4\n")
				.append("To go back to \"AtmDepartment\" -> 5\n");
		ui.print(sb.toString());
		return handleActionChoiceInput();
	}

	private AtmStates handleActionChoiceInput() {
		Map<Integer, AtmStates> statesMap = AtmUtil.getStatesMap();
		int choise = 0;
		try {
			choise = Integer.parseInt(String.valueOf(ui.read().trim().charAt(0)));
			if(choise < 1 || choise > statesMap.size()) {
				throw new IndexOutOfBoundsException("Value out of bound");
			}
		}catch (NumberFormatException | IndexOutOfBoundsException  ex){
			ui.print("\nYou input unsupported value,\ntry again!\n");
			return AtmStates.ACTION_CHOICE;
		}
		return statesMap.get(choise);
	}
}
