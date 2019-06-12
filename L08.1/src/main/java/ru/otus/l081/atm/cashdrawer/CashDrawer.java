package ru.otus.l081.atm.cashdrawer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CashDrawer implements Serializable {
	private List currenciesNames;
	private List<CurrencyCells> storage;

	public CashDrawer() {
		storage = new ArrayList<>();
	}
	public List<String> getCurrenciesNamesList() {
		return currenciesNames;
	}

	public List getDenominationsListByCurrencyName(String currencyName) {
		return storage.get(currenciesNames.indexOf(currencyName.trim().toUpperCase())).getDenominations();
	}

	public List getNumberOfBanknotesListByCurrencyName(String currentCurrency) {

		return storage.get(currenciesNames.indexOf(currentCurrency)).getValuesFromBanknoteCells();
	}

	public Integer totalSum(String currCurrency) {
		return storage.get(currenciesNames.indexOf(currCurrency)).getSum();
	}

	public void addBanknotes(String currentCurrency, Integer denomination, Integer numberOfBanknotesToDeposit) {
		CurrencyCells currencyCells = storage.get(currenciesNames.indexOf(currentCurrency));
		BanknoteCell cell = currencyCells.getCell(denomination);
		Integer currentNumOfBanknotes = cell.getNumberOfBanknotes();
		cell.setNumberOfBanknotes(currentNumOfBanknotes + numberOfBanknotesToDeposit);
	}

	public Integer getMinAvailableBanknote(String currCurrency) {
		return storage.get(currenciesNames.indexOf(currCurrency)).getMinAvailableBanknote();
	}

	public void setNumberOfBanknotes(String currentCurrency, List saveToCurrencyCells) {
		CurrencyCells currencyCells = storage.get(currenciesNames.indexOf(currentCurrency));
		for (int i = 0; i < currencyCells.size(); i++) {
			currencyCells.setNumberOfBanknotesByCellIndex(i, (Integer) saveToCurrencyCells.get(i));
		}
	}

	public void setCurrenciesList(List currenciesList) {
		currenciesNames = currenciesList;
	}

	public void setStorage(List<CurrencyCells> storage) {
		this.storage = storage;
	}

	public String getBalance() {
		List<String> currNames = getCurrenciesNamesList();
		StringBuilder sb = new StringBuilder();
		for (String currName : currNames) {
			sb.append("Currency: ").append(currName).append(", Total balance: ").append(totalSum(currName));
			List denominations = getDenominationsListByCurrencyName(currName);
			List numberOfBanknotes = getNumberOfBanknotesListByCurrencyName(currName);
			for (int i = 0; i < denominations.size(); i++) {
				sb.append("\n")
						.append(denominations.get(i))
						.append(" : ")
						.append(numberOfBanknotes.get(i));
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}