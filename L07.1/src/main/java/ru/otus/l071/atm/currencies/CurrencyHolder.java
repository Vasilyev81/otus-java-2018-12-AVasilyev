package ru.otus.l071.atm.currencies;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CurrencyHolder implements Cloneable {
	private static CurrenciesBasket basket;
	private static CurrencyHolder instance;
	private static Map<Integer, Integer> currentCurrency;
	private static String currencyName;

	private CurrencyHolder() {
		basket = CurrenciesBasket.getCurrencyBasket();
	}

	public static CurrencyHolder getCurrencyHolder() {
		if (instance == null) instance = new CurrencyHolder();
		return instance;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public <K extends Integer, V extends Integer> V put(K k, V v) {
		return (V) currentCurrency.put(k, v);
	}

	public <K extends Integer, V extends Integer> V get(K k) {
		return (V) currentCurrency.get(k);
	}

	public Set<Map.Entry<Integer, Integer>> entrySet() {
		return currentCurrency.entrySet();
	}

	public Set<Integer> keySet() {
		return currentCurrency.keySet();
	}

	public void setCurrency(String name) {
		currencyName = name;
		currentCurrency = basket.getCurrency(name);
	}

	public Map getTransactionCopy() {
		Map<Integer, Integer> transactionCopy = new LinkedHashMap<>();
		transactionCopy = currentCurrency.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		return transactionCopy;
	}

	public Map getEmptyTransaction() {
		Map<Integer, Integer> transaction = new LinkedHashMap<>();
		currentCurrency.keySet().forEach(x -> transaction.put(x, 0));
		return transaction;
	}

	public void applyTransaction(Map<Integer, Integer> transaction) {
		currentCurrency = transaction;
	}

	public Map<String, LinkedHashMap<Integer, Integer>> getCurrenciesBasketState() {
		return basket.getCurrienciesBasketState();
	}
}
