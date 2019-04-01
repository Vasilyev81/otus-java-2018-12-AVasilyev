package ru.otus.l072.cashdrawer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CurrencyCells implements Serializable {
	private List<Integer> denominations;
	private List<BanknoteCell> banknoteCells;

	public CurrencyCells(List<Integer> denominations, List<BanknoteCell> banknoteCells) {
		this.denominations = denominations;
		this.banknoteCells = banknoteCells;
	}

	public List<Integer> getDenominations() {
		return denominations;
	}

	public BanknoteCell getCell(Integer denomination) {
		return banknoteCells.get(denominations.indexOf(denomination));
	}

	public Integer getSum() {
		int result = 0;
		for (int i = 0; i < denominations.size(); i++) {
			result += denominations.get(i) * banknoteCells.get(i).getNumberOfBanknotes();
		}
		return result;
	}

	public Integer getMinAvailableBanknote() {
		for (int index = 0; index < banknoteCells.size(); index++) {
			if (banknoteCells.get(index).getNumberOfBanknotes() > 0) return denominations.get(index);
		}
		return 0;
	}

	public List getValuesFromBanknoteCells() {
		List result = new ArrayList<>();
		for (BanknoteCell banknoteCell : banknoteCells) {
			int numberOfBanknotes = banknoteCell.getNumberOfBanknotes();
			result.add(numberOfBanknotes);
		}
		return result;
	}

	public int size() {
		return banknoteCells.size();
	}

	public void setNumberOfBanknotesByCellIndex(int index, int value) {
		banknoteCells.get(index).setNumberOfBanknotes(value);
	}

	public void setDenominations(List<Integer> denominations) {
		this.denominations = denominations;
	}
}
