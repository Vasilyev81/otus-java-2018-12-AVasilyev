package ru.otus.l081.atm.cashdrawer;

import java.io.Serializable;

public class BanknoteCell implements Serializable {
	private final Banknote banknote;
	private int quantity;

	public BanknoteCell(Banknote banknote, int quantity) {
		this.banknote = banknote;
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	void addToQuantity(int value) {
		quantity += value;
	}

	public int getBalance() {
		return banknote.getNominal() * quantity;
	}

	@Override
	public String toString() {
		return new StringBuilder().append(banknote.toString()).append(" -> ").append(quantity).append("\n").toString();
	}

	public int getNominal() {
		return banknote.getNominal();
	}
}
