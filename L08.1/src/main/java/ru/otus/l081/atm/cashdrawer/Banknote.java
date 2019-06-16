package ru.otus.l081.atm.cashdrawer;

public enum Banknote {
	USD1(Currency.USD, 1),
	USD2(Currency.USD, 2),
	USD5(Currency.USD, 5),
	USD10(Currency.USD, 10),
	USD50(Currency.USD, 50),
	USD100(Currency.USD, 100),
	EUR5(Currency.EUR, 5),
	EUR10(Currency.EUR, 10),
	EUR20(Currency.EUR, 20),
	EUR50(Currency.EUR, 50),
	EUR100(Currency.EUR, 100),
	EUR200(Currency.EUR, 200),
	EUR500(Currency.EUR, 500),
	RUR50(Currency.RUR, 50),
	RUR100(Currency.RUR, 100),
	RUR200(Currency.RUR, 200),
	RUR500(Currency.RUR, 500),
	RUR1000(Currency.RUR, 1000),
	RUR2000(Currency.RUR, 2000),
	RUR5000(Currency.RUR, 5000);

	private Currency currency;
	private int nominal;

	Banknote(Currency currency, int nominal) {
		this.currency = currency;
		this.nominal = nominal;
	}

	public Currency getCurrency() {
		return currency;
	}

	public int getNominal() {
		return nominal;
	}

	@Override
	public String toString() {
		return new StringBuilder().append(currency).append(",").append(nominal).toString();
	}
}
