package ru.otus.l081.atm.transactions;

import ru.otus.l081.atm.cashdrawer.BanknoteCell;
import ru.otus.l081.atm.cashdrawer.CashBox;
import ru.otus.l081.atm.cashdrawer.Currency;

import java.util.ArrayList;
import java.util.List;

public class Transaction {
	private CashBox cashBox;
	private Currency currentCurrency;

	public Transaction(CashBox cashBox, Currency currentCurrency) {
		this.cashBox = cashBox;
		this.currentCurrency = currentCurrency;
	}

	public void deposit(int denomination, int numberOfBanknotes) {
		cashBox.addBanknotes(currentCurrency, denomination, numberOfBanknotes);
	}

	public String withdraw(int withdrawValue) {
		int withdrawValueForCheck = withdrawValue;
		List<BanknoteCell> nowInCashBox = cashBox.getCurrencyBox(currentCurrency).getInternalStorage();
		List<BanknoteCell> withdrawToUser = CashBoxUtil.createZeroQuantityCurrencyBox(currentCurrency);
		List<BanknoteCell> saveToCashDrawer = CashBoxUtil.createZeroQuantityCurrencyBox(currentCurrency);
		int nominal;
		int availableNumOfBanknotes;
		for (int index = nowInCashBox.size() - 1; index >= 0; index--) {
			BanknoteCell currentBanknote = nowInCashBox.get(index);
			nominal = currentBanknote.getNominal();
			availableNumOfBanknotes = currentBanknote.getQuantity();
			BanknoteCell toUser = withdrawToUser.get(index);
			BanknoteCell toCashDrawer = saveToCashDrawer.get(index);
			if (withdrawValue >= nominal) {
				int quotient = withdrawValue / nominal;
				if (quotient >= availableNumOfBanknotes) {
					quotient = availableNumOfBanknotes;
					toCashDrawer.setQuantity(0);
					toUser.setQuantity(quotient);
					withdrawValue -= quotient * nominal;
				} else {
					toCashDrawer.setQuantity(availableNumOfBanknotes - quotient);
					toUser.setQuantity(quotient);
					withdrawValue -= quotient * nominal;
				}
			} else {
				toUser.setQuantity(0);
				toCashDrawer.setQuantity(availableNumOfBanknotes);
			}
		}
		saveToCashDrawer = reverse(saveToCashDrawer);
		withdrawToUser = reverse(withdrawToUser);
		String result;
		if (checkTransaction(withdrawToUser, withdrawValueForCheck)) {
			cashBox.setCurrencyBox(currentCurrency, saveToCashDrawer);
			result = getUserOutputString(withdrawToUser);
		} else result = "Something went wrong\n";
		return result;
	}

	private String getUserOutputString(List<BanknoteCell> withdrawToUser) {
		StringBuilder sb = new StringBuilder();
		for (BanknoteCell banknoteCell : withdrawToUser) {
			if (banknoteCell.getBalance() > 0) {
				sb.append(currentCurrency.name())
						.append(",")
						.append(banknoteCell.getNominal())
						.append(" -> ")
						.append(banknoteCell.getQuantity())
						.append("\n");
			}
		}
		return sb.toString();
	}

	private boolean checkTransaction(List<BanknoteCell> withdrawToUser, int withdrawValue) {
		int check = 0;
		for (BanknoteCell banknoteCell : withdrawToUser) {
			if (banknoteCell.getBalance() > 0) check += banknoteCell.getBalance();
		}
		return check == withdrawValue;
	}

	private <T> List<T> reverse(List<T> list) {
		List<T> reversedList = new ArrayList<>();
		for (int i = list.size() - 1; i >= 0; i--) {
			reversedList.add(list.get(i));
		}
		return reversedList;
	}

	public int getMinAvailableNominal() {
		return cashBox.getMinAvailableNominal(currentCurrency);
	}

	public int getTotalBalanceForCurrentCurrency() {
		return cashBox.totalSum(currentCurrency);
	}

	public List getDenominationsList() {
		return cashBox.getNominalsListByCurrencyName(currentCurrency);
	}

	public void setCurrentCurrency(Currency currency) {
		currentCurrency = currency;
	}

	public Currency getCurrentCurrency() {
		return currentCurrency;
	}

	public String getBalanceAsString() {
		return cashBox.getBalanceAsString();
	}
}
