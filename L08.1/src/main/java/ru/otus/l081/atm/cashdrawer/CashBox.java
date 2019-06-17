package ru.otus.l081.atm.cashdrawer;

import ru.otus.l081.atm.transactions.CashBoxUtil;

import java.io.Serializable;
import java.util.*;

public class CashBox implements Serializable {
	private EnumMap<Currency, CurrencyBox> storage;

	public CashBox() {
		storage = CashBoxUtil.createInitialCashBoxValues();
	}

	public String getBalanceAsString() {
		StringBuilder sb = new StringBuilder();
		for (Currency cur : Currency.values()) {
			sb.append(storage.get(cur).getBalanceAsString());
		}
		return sb.toString();
	}

	public List getNominalsListByCurrencyName(Currency currentCurrency) {
		return storage.get(currentCurrency).getNominalsList();
	}

	public void addBanknotes(Currency currentCurrency, int nominal, int numberOfBanknotes) {
		storage.get(currentCurrency).addBanknotes(nominal, numberOfBanknotes);
	}

	public int getMinAvailableNominal(Currency currentCurrency) {
		return storage.get(currentCurrency).getMinAvailableNominal();
	}

	public int totalSum(Currency currentCurrency) {
		return storage.get(currentCurrency).getBalance();
	}

	public CurrencyBox getCurrencyBox(Currency currentCurrency) {
		return storage.get(currentCurrency);
	}

	public void setCurrencyBox(Currency currentCurrency, List<BanknoteCell> currencyBox) {
		storage.put(currentCurrency, new CurrencyBox(currentCurrency, currencyBox));
	}
}