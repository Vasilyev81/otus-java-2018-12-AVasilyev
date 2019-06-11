package ru.otus.l081.atm.cashdrawer;

import java.io.Serializable;

public class BanknoteCell implements Serializable {
	private Integer numberOfBanknotes;

	public BanknoteCell(Integer numberOfBanknotes) {
		this.numberOfBanknotes = numberOfBanknotes;
	}

	public Integer getNumberOfBanknotes() {
		return numberOfBanknotes;
	}

	public void setNumberOfBanknotes(Integer numberOfBanknotes) {
		this.numberOfBanknotes = numberOfBanknotes;
	}
}
