package ru.otus.l072;

import ru.otus.l072.cashdrawer.BanknoteCell;
import ru.otus.l072.cashdrawer.CashDrawer;
import ru.otus.l072.cashdrawer.CurrencyCells;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Utils {
	private Transaction transaction;

	public Utils(Transaction transaction) {
		this.transaction = transaction;
	}

	public static List<String> getCurrenciesNames() {
		return Arrays.asList("USD", "EUR", "RUR");
	}

	public List getDenominationsListByCurrencyName(String currency) {
		List result = null;
		switch (currency) {
			case ("USD"): {
				result = Arrays.asList(1, 2, 5, 10, 20, 50, 100);
				break;
			}
			case ("EUR"): {
				result = Arrays.asList(5, 10, 20, 50, 100, 200, 500);
				break;
			}
			case ("RUR"): {
				result = Arrays.asList(50, 100, 200, 500, 1000, 2000, 5000);
				break;
			}
		}
		return result;
	}

	public void initCashDrawer(CashDrawer cashDrawer) {
		if(cashDrawer == null) System.out.println("cashDrawer = null!");
		List<String> currenciesNamesList = getCurrenciesNames();
		cashDrawer.setCurrenciesList(currenciesNamesList);
		List<CurrencyCells> storage = new ArrayList<>();
		Random rnd = new Random();
		for (String currency : currenciesNamesList) {
			List denominations = getDenominationsListByCurrencyName(currency);
			List<BanknoteCell> initialValues = new ArrayList<>();
			for(int i = 0; i < denominations.size(); i++){
				initialValues.add(new BanknoteCell(rnd.nextInt(11)));
			}
			CurrencyCells cells = new CurrencyCells(denominations, initialValues);
			storage.add(cells);
			cashDrawer.setStorage(storage);
			transaction.setCashDrawer(cashDrawer);
		}
	}
}
