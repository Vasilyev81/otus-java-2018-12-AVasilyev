package ru.otus.l081.atm.actions;

import ru.otus.l081.atm.AtmStates;
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
		int choise;
		try {
			choise = Integer.parseInt(String.valueOf(ui.read().trim().charAt(0)));
		}catch (NumberFormatException ex){
			ui.print("\nYou input unsupported value,\ntry again!");
			return AtmStates.ACTION_CHOICE;
		}
		Map<Integer, AtmStates> statesMap = new HashMap<>();
		statesMap.put(1, AtmStates.DEPOSIT);
		statesMap.put(2, AtmStates.WITHDRAW);
		statesMap.put(3, AtmStates.CURRENCY_CHOICE);
		statesMap.put(4, AtmStates.CURRENCY_BALANCE);
		statesMap.put(5, AtmStates.FINISH_WORK);
		return statesMap.get(choise); //TODO> NPE throws after getting "5" from this map
	}
}
