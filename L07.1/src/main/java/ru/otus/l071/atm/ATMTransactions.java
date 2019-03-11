package ru.otus.l071.atm;

import ru.otus.l071.atm.currencies.CurrencyHolder;

import java.util.*;
import java.util.stream.Collectors;

public class ATMTransactions {
	private static CurrencyHolder currentCurrency;

	public ATMTransactions() {
		currentCurrency = CurrencyHolder.getCurrencyHolder();
	}

	public void depositCash(Map<Integer, Integer> inputTransaction) {
		inputTransaction.keySet().forEach(key -> currentCurrency.put(key, (currentCurrency.get(key) + inputTransaction.get(key))));
	}

	public <K extends Integer, V extends Integer> Map<Integer, Integer> calculateCashWithdraw(Map<K, V> availableBanknotes, Integer requiredTransaction) {
		Map<Integer, Integer> transactionByBanknotes = currentCurrency.getEmptyTransaction();
		Map<Integer, Integer> reversedAvailableBanknotes = reverseTransaction(availableBanknotes);
		for (Map.Entry entry : reversedAvailableBanknotes.entrySet()) {
			int key = (int) entry.getKey();
			if (requiredTransaction >= key) {
				int v1 = requiredTransaction / key;
				requiredTransaction -= key * v1;
				transactionByBanknotes.put(key, v1);
			}
		}
		return transactionByBanknotes;
	}

	private <V extends Integer, K extends Integer> Map<Integer, Integer> reverseTransaction(Map<K, V> availableBanknotes) {
		return availableBanknotes.entrySet().stream().sorted(Map.Entry.<K, V>comparingByKey().reversed()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));//TODO: How this shit works?????
	}

	public Map getAvailableBanknotes() {
		Map<Integer, Integer> availableBanknotes = new LinkedHashMap<>();
		for (Integer key : currentCurrency.keySet()) {
			if (currentCurrency.get(key) > 0) availableBanknotes.put(key, currentCurrency.get(key));
		}
		return availableBanknotes;
	}

	public Integer calculateAmount(Map<Integer, Integer> transaction) {
		return transaction.entrySet().stream().map(entry -> (entry.getKey() * entry.getValue())).reduce(0, (acc, x) -> acc + x);
	}

	public Integer calculateAmount() {
		return currentCurrency.entrySet().stream().map(entry -> (entry.getKey() * entry.getValue())).reduce(0, (acc, x) -> acc + x);
	}

	public Integer getMinAvailableBanknote(Map<Integer, Integer> availableBanknotes) {
		return availableBanknotes.keySet().stream().min(Comparator.comparingInt(x -> x)).get();
	}

	public void withdrawTransaction(Map<Integer, Integer> withdrawTransaction, Integer requiredTransaction) throws TransactionException {
		Map<Integer, Integer> transaction = currentCurrency.getTransactionCopy();
		Integer amountBefore = calculateAmount(transaction);
		for (Integer key : withdrawTransaction.keySet()) {
			transaction.put(key, transaction.get(key) - withdrawTransaction.get(key));
		}
		Integer amountAfter = calculateAmount(transaction);
		System.out.println("amountAfter ->" + amountAfter + "amountBefore ->" + amountBefore + "requiredTransaction ->" + requiredTransaction);
		if (amountAfter != amountBefore - requiredTransaction) throw new TransactionException("Something went wrong");
		currentCurrency.applyTransaction(transaction);
	}

	public void setCurrentCurrency(String currency) {
		currentCurrency.setCurrency(currency);
	}

	public Map<Integer, Integer> getEmptyTransaction() {
		return currentCurrency.getEmptyTransaction();
	}

	public String getCurrencyName() {
		return currentCurrency.getCurrencyName();
	}

	public Map<String, LinkedHashMap<Integer, Integer>> getCurrenciesBasketState() {
		return  currentCurrency.getCurrenciesBasketState();
	}
}


