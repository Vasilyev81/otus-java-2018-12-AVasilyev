package ru.otus.l081.atm;

		import java.util.*;

public class AtmUtil {
	private static Map<Integer, AtmStates> statesMap;

	public static void init() {
		statesMap = new HashMap<>();
		statesMap.put(1, AtmStates.DEPOSIT);
		statesMap.put(2, AtmStates.WITHDRAW);
		statesMap.put(3, AtmStates.CURRENCY_CHOICE);
		statesMap.put(4, AtmStates.CURRENCY_BALANCE);
		statesMap.put(5, AtmStates.FINISH_WORK);
	}

	public static Map<Integer, AtmStates> getStatesMap() {
		return statesMap;
	}
}
