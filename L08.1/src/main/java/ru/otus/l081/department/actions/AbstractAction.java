package ru.otus.l081.department.actions;

import ru.otus.l081.department.Department;

public interface AbstractAction {
	Department.States execute();
}
