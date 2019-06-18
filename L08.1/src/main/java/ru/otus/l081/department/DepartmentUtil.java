package ru.otus.l081.department;

import java.util.HashMap;
import java.util.Map;

public class DepartmentUtil {
	private static Map<Integer, DepartmentStates> statesMap;

	public static void init() {
		statesMap = new HashMap<>();
		statesMap.put(1, DepartmentStates.WORK_WITH_ATM);
		statesMap.put(2, DepartmentStates.GET_ATM_BALANCE);
		statesMap.put(3, DepartmentStates.GET_ALL_ATM_BALANCE);
		statesMap.put(4, DepartmentStates.RESET_ATM);
		statesMap.put(5, DepartmentStates.RESET_ALL_ATM);
		statesMap.put(6, DepartmentStates.STOP);
	}

	public static Map<Integer, DepartmentStates> getStatesMap() {
		return statesMap;
	}
}
