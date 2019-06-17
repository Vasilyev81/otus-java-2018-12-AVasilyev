package ru.otus.l081.atm.cashdrawer;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class CurrencyBox implements Serializable {
	private final Currency currency;
	private final List<BanknoteCell> storage;

	public CurrencyBox(Currency currency, List<BanknoteCell> storage) {
		this.currency = currency;
		this.storage = storage;
	}

	String getBalanceAsString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Currency: ").append(currency.name()).append("\n");
		sb.append("Total sum: ").append(storage.stream().map(BanknoteCell::getBalance).reduce(0, Integer::sum)).append("\n");
		sb.append(storage.stream().map(BanknoteCell::toString).collect(Collectors.joining("", "", "\n")));
		return sb.toString();
	}

	List getNominalsList() {
		return storage.stream().map(BanknoteCell::getNominal).collect(Collectors.toList());
	}

	void addBanknotes(int nominal, int numberOfBanknotes) {
		storage.stream().filter(banknoteCell -> banknoteCell.getNominal() == nominal).findFirst().get().addToQuantity(numberOfBanknotes);
	}

	int getMinAvailableNominal() {
		return storage.stream().filter(x -> x.getQuantity() > 0).findFirst().map(BanknoteCell::getNominal).get();
	}

	public List<BanknoteCell> getInternalStorage() {
		return storage;
	}

	int getBalance() {
		return storage.stream().map(BanknoteCell::getBalance).reduce(0, Integer::sum);
	}
}
