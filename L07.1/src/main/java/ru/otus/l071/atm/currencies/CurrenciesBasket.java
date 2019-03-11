package ru.otus.l071.atm.currencies;

import java.util.*;

public class CurrenciesBasket {
	private static CurrenciesBasket instance;
	private Map<String, LinkedHashMap<Integer, Integer>> currenciesHolder;
	private final static List currenciesList = Arrays.asList("USD", "EUR", "RUR");
	private final static List<Integer> usdBanknotesList = Arrays.asList(1, 2, 5, 10, 20, 50, 100);
	private final static List<Integer> eurBanknotesList = Arrays.asList(5, 10, 20, 50, 100, 200, 500);
	private final static List<Integer> rurBanknotesList = Arrays.asList(50, 100, 200, 500, 1000, 2000, 5000);

	private CurrenciesBasket() {
		currenciesHolder = new HashMap<>();

		currenciesList.stream().forEach(x -> currenciesHolder.put((String) x, new LinkedHashMap<>()));
		Random rnd = new Random();
		LinkedHashMap<Integer, Integer> usd;
		usd = new LinkedHashMap<>();
		usdBanknotesList.forEach(x -> usd.put(x, rnd.nextInt(11)));
		LinkedHashMap<Integer, Integer> eur = new LinkedHashMap<>();
		eurBanknotesList.forEach(x -> eur.put(x, rnd.nextInt(11)));
		LinkedHashMap<Integer, Integer> rur = new LinkedHashMap<>();
		rurBanknotesList.forEach(x -> rur.put(x, rnd.nextInt(11)));
		currenciesHolder.put("USD", usd);
		currenciesHolder.put("EUR", eur);
		currenciesHolder.put("RUR", rur);
	}

	public static CurrenciesBasket getCurrencyBasket() {
		if (instance == null) instance = new CurrenciesBasket();
		return instance;
	}

	public Map<Integer, Integer> getCurrency(String currency) {
		return currenciesHolder.get(currency);
	}

	public Map<String, LinkedHashMap<Integer, Integer>> getCurrienciesBasketState(){
		return currenciesHolder;
	}

}
