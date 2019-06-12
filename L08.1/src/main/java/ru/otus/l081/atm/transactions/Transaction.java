package ru.otus.l081.atm.transactions;

import ru.otus.l081.atm.cashdrawer.CashDrawer;

import java.util.ArrayList;
import java.util.List;

public class Transaction {
	private CashDrawer cashDrawer;
	private String currentCurrency;

	//TODO: Move all methods, except deposit, withdraw and checkTransaction, to CashDrawer.

	public Transaction(CashDrawer cashDrawer, String currentCurrency) {
		this.cashDrawer = cashDrawer;
		this.currentCurrency = currentCurrency;
	}

	public void deposit(Integer denomination, Integer numberOfBanknotes) {
		cashDrawer.addBanknotes(currentCurrency, denomination, numberOfBanknotes);
	}

	public String withdraw(Integer withdrawValue) throws TransactionException {
		int withdrawValueForCheck = withdrawValue;
		List<Integer> denominationsList = getDenominationsListByCurrencyName(currentCurrency);
		List<Integer> numberOfBanknotesList = cashDrawer.getNumberOfBanknotesListByCurrencyName(currentCurrency);
		List<Integer> withdrawToUser = new ArrayList();
		List<Integer> saveToCashdrawer = new ArrayList();
		int denomination;
		int numOfBanknotes;
		for (int index = numberOfBanknotesList.size() - 1; index >= 0; index--) {
			denomination = denominationsList.get(index);
			numOfBanknotes = numberOfBanknotesList.get(index);
			if (withdrawValue >= denomination) {
				int quotient = withdrawValue / denomination;
				if (quotient >= numOfBanknotes) {
					quotient = numOfBanknotes;
					saveToCashdrawer.add(0);
					withdrawToUser.add(quotient);
					withdrawValue -= quotient*denomination;
				} else{
					saveToCashdrawer.add(numOfBanknotes-quotient);
					withdrawToUser.add(quotient);
					withdrawValue -= quotient*denomination;
				}
			}
			else {
				withdrawToUser.add(0);
				saveToCashdrawer.add(numOfBanknotes);
			}
		}
		saveToCashdrawer = reverse(saveToCashdrawer);
		withdrawToUser = reverse(withdrawToUser);
		String userWithdraw = checkTransaction(withdrawToUser, denominationsList, withdrawValueForCheck);
		cashDrawer.setNumberOfBanknotes(currentCurrency, saveToCashdrawer);
		return userWithdraw;
	}

	private String checkTransaction(List<Integer> withdrawToUser, List<Integer> denominationsList, Integer withdrawValue) throws TransactionException {
		int check = 0;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < withdrawToUser.size(); i++) {
			System.out.println(denominationsList.get(i) + ": " + withdrawToUser.get(i));
			check += withdrawToUser.get(i) * denominationsList.get(i);
			sb.append(denominationsList.get(i))
					.append(" ")
					.append(currentCurrency)
					.append(":")
					.append(withdrawToUser.get(i))
					.append("\n");
		}
		if (check != withdrawValue) throw new TransactionException("Something went wrong");
		return sb.toString();
	}

	private <T> List<T> reverse(List<T> list) {
		List<T> reversedList = new ArrayList<>();
		for (int i = list.size() - 1; i >= 0; i--) {
			reversedList.add(list.get(i));
		}
		return reversedList;
	}

	public List getDenominationsListByCurrencyName(String currentCurrency) {
		return cashDrawer.getDenominationsListByCurrencyName(currentCurrency);
	}

	public Integer getMinAvailableDenomination() {
		return cashDrawer.getMinAvailableBanknote(currentCurrency);
	}

	public Integer getAvailableCashForCurrentCurrency() {
		return cashDrawer.totalSum(currentCurrency);
	}
	public Integer getAvailableCashByCurrencyName(String currName) {
		return cashDrawer.totalSum(currName);
	}

	public List getDenominationsList() {
		return cashDrawer.getDenominationsListByCurrencyName(currentCurrency);
	}

	public List<String> getCurrenciesNamesList() {
		return cashDrawer.getCurrenciesNamesList();
	}

	public void setCurrency(String reducedChoice) {
		currentCurrency = reducedChoice;
	}

	public void setCashDrawer(CashDrawer cashDrawer) {
		this.cashDrawer = cashDrawer;
	}

	public String getCurrency() {
		return currentCurrency;
	}
}
