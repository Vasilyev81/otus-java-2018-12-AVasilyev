package ru.otus.l081.atm.transactions;

import java.util.Map;

public interface AtmMementoInterface {
	boolean restore(Map<String, AtmMementoInterface> store);
}
