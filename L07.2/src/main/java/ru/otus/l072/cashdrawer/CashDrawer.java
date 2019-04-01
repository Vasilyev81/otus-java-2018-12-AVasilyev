package ru.otus.l072.cashdrawer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CashDrawer implements Serializable {
	private static CashDrawer instance;
	private List<String> currenciesNames;
	private List<CurrencyCells> storage;

	private CashDrawer() {
		storage = new ArrayList<>();
	}

	public static CashDrawer getCashDrawer() {
		if (instance == null) instance = new CashDrawer();
		return instance;
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
		BanknoteCell cell = currencyCells.getCell(denomination);//
		Integer currentNumOfBanknotes = cell.getNumberOfBanknotes();
		cell.setNumberOfBanknotes(currentNumOfBanknotes + numberOfBanknotesToDeposit);
	}

	public Integer getMinAvailableBanknote(String currCurrency) {
		return storage.get(currenciesNames.indexOf(currCurrency)).getMinAvailableBanknote();
	}

	public void setNumberOfBanknotes(String currentCurrency, List saveToCurrencyCells){
		CurrencyCells currencyCells= storage.get(currenciesNames.indexOf(currentCurrency));
		for(int i = 0; i < currencyCells.size(); i ++){
			currencyCells.setNumberOfBanknotesByCellIndex(i, (Integer) saveToCurrencyCells.get(i));
		}
	}

	public void setCurrenciesList(List currenciesList) {
		currenciesNames = currenciesList;
	}

	public void setStorage(List<CurrencyCells> storage) {
		this.storage = storage;
	}
}
