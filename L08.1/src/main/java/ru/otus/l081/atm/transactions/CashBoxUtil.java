package ru.otus.l081.atm.transactions;

import ru.otus.l081.atm.cashdrawer.*;
import ru.otus.l081.atm.cashdrawer.Currency;

import java.util.*;
import java.util.stream.Collectors;

public class CashBoxUtil {

	public static EnumMap<Currency, CurrencyBox> createInitialCashBoxValues() {
		EnumMap<Currency, CurrencyBox> storage = new EnumMap<>(Currency.class);
		Random rnd = new Random();
		for (Currency currency: Currency.values()) {
			List<BanknoteCell> currencyCells = Arrays.stream(Banknote.values()).filter(banknote -> banknote.getCurrency() == currency)
					.map(banknote -> new BanknoteCell(banknote, rnd.nextInt(11))).collect(Collectors.toList());
						storage.put(currency, new CurrencyBox(currency, currencyCells));
		}
		return storage;
	}

	public static List<BanknoteCell> createZeroQuantityCurrencyBox(Currency currency) {
		return Arrays.stream(Banknote.values()).filter(banknote -> banknote.getCurrency() == currency)
				.map(banknote -> new BanknoteCell(banknote, 0)).collect(Collectors.toList());
	}
}
