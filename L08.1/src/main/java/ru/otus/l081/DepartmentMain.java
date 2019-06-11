package ru.otus.l081;

import ru.otus.l081.department.Department;

public class DepartmentMain {
	public static void main(String[] args) {
		int argument = 0;
		if(args.length > 0) argument = Integer.valueOf(args[0]);
		int atms = (argument > 0) ? argument : 5;
		Department department = new Department(atms);
		department.start();

	}
}
