package ru.otus.l071.atm;

import java.util.LinkedHashMap;
import java.util.Map;

public class ATMStatesLogic implements Runnable {
	private static States state;

	enum States {
		INIT, CURRENCY_CHOICE, ACTION_CHOICE, DEPOSIT, WITHDRAW, INFO
	}

	private ATMTransactions atmTransactions;
	private CLInterface uInterface;

	public ATMStatesLogic(ATMTransactions atmTransactions, CLInterface clInterface) {
		this.atmTransactions = atmTransactions;
		uInterface = clInterface;
		state = States.INIT;
	}

	@Override
	public void run() {
		while (true) {
			switch (state) {
				case INIT: {
					caseInit();
					break;
				}
				case CURRENCY_CHOICE: {
					caseCurrencyChoice();
				}
				case ACTION_CHOICE: {
					caseActionChoice();
					break;
				}
				case DEPOSIT: {
					caseDeposit();
					break;
				}
				case WITHDRAW: {
					caseWithdraw();
					break;
				}
				case INFO: {
				}
			}
		}
	}

	private void caseInit() {
		uInterface.print("ATM initialization: ");
		//for (int i = 0; i < 10; i++) {uInterface.print("* ");try { Thread.sleep(200);} catch (InterruptedException e) {e.printStackTrace();}}
		state = States.CURRENCY_CHOICE;
	}

	private void caseCurrencyChoice() {
		uInterface.print("\nYou can choose between USD, EUR or RUR");
		uInterface.print("\nFor USD type \"1\"");
		uInterface.print("\nFor EUR type \"2\"");
		uInterface.print("\nFor RUR type \"3\"");
		uInterface.print("\nYour choice is:\n->");
		handleCurrencyChoiceInput(uInterface.read());
	}

	private void handleCurrencyChoiceInput(String choice) {
		switch (choice) {
			case ("1"): {
				uInterface.print("\nYou choose USD");
				setCurrentCurrency("USD");
				state = States.ACTION_CHOICE;
				break;
			}
			case ("2"): {
				uInterface.print("\nYou choose EUR");
				setCurrentCurrency("EUR");
				state = States.ACTION_CHOICE;
				break;
			}
			case ("3"): {
				uInterface.print("\nYou choose RUR");
				setCurrentCurrency("RUR");
				state = States.ACTION_CHOICE;
				break;
			}
			default: {
				uInterface.print("\nYou input unsupported value,\ntry again!");
				state = States.CURRENCY_CHOICE;
			}
		}
	}

	private void setCurrentCurrency(String currency) {
		atmTransactions.setCurrentCurrency(currency);
	}

	private void caseActionChoice() {
		uInterface.print("\nYou can deposit or withdraw money.");
		uInterface.print("\nTo pass type \"1\"");
		uInterface.print("\nTo withdraw type \"2\"");
		uInterface.print("\nTo go back to currency choice \"3\"");
		uInterface.print("\nYour choice is:\n->");
		handleActionChoiceInput(uInterface.read());
	}

	private void handleActionChoiceInput(String choice) {
		switch (choice) {
			case ("1"): {
				state = States.DEPOSIT;
				break;
			}
			case ("2"): {
				state = States.WITHDRAW;
				break;
			}
			case ("3"): {
				state = States.CURRENCY_CHOICE;
				break;
			}
			default: {
				uInterface.print("\nYou input unsupported value,\ntry again!");
				state = States.ACTION_CHOICE;
			}
		}
	}

	private void caseDeposit() {
		Map<Integer, Integer> passTransaction = handleDepositInput();
		atmTransactions.depositCash(passTransaction);
		uInterface.print("\nCalculating amount:\nsumm is:" + atmTransactions.calculateAmount());
		state = States.ACTION_CHOICE;
	}

	private Map<Integer, Integer> handleDepositInput() {
		Map<Integer, Integer> passTransaction = atmTransactions.getEmptyTransaction();
		uInterface.print("\nInput number of passed banknotes for each banknote value.");
		for (Integer key : passTransaction.keySet()) {
			boolean numberIsIncorrect = true;
			Integer numberOfBanknotes = null;
			while (numberIsIncorrect) {
				uInterface.print("\nBanknote value: " + key + atmTransactions.getCurrencyName() + ", type number of passed banknotes:");
				String userInput = uInterface.read();
				try {
					numberOfBanknotes = Integer.parseInt(userInput);
				} catch (NumberFormatException e) {
					uInterface.print("\nYou input incorrect value, try again");
				}
				if (numberOfBanknotes != null) numberIsIncorrect = false;
			}
			passTransaction.put(key, numberOfBanknotes);
		}
		return passTransaction;
	}

	private void caseWithdraw() {
		Map availableBanknotes = atmTransactions.getAvailableBanknotes();
		Integer minAvailableBanknote = atmTransactions.getMinAvailableBanknote(availableBanknotes);
		System.out.println("availableBanknotes" + availableBanknotes);
		uInterface.print("\nEnter the required amount of money: ");
		uInterface.print("\nRequired amount must be a multiple of : " + minAvailableBanknote + "\n->");
		Integer requiredAmount = handleWithdrawInput(minAvailableBanknote);
		Map<Integer, Integer> withdrawTransaction = atmTransactions.calculateCashWithdraw(availableBanknotes, requiredAmount);
		try {
			atmTransactions.withdrawTransaction(withdrawTransaction, requiredAmount);
		} catch (TransactionException e) {
			e.getMessage();
		}
		state = States.ACTION_CHOICE;
	}

	private Integer handleWithdrawInput(Integer minAvailableBanknote) {
		Integer result = 0;
		boolean requiredValueIsNotValid = true;
		while (requiredValueIsNotValid) {
			String input = uInterface.read();
			try {
				result = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				uInterface.print("\nInputted result is not a number,\ntry again:");
				continue;
			}
			if (result % minAvailableBanknote != 0) {
				uInterface.print("\nInputted result is not multiple of: " + minAvailableBanknote + "\ntry again:");
				continue;
			}
			if (result > atmTransactions.calculateAmount()) {
				uInterface.print("\nNot enough money for withdraw the required amount,\ntry entering a lower result:");
				continue;
			}
			requiredValueIsNotValid = false;
		}
		return result;
	}
	public Map<String, LinkedHashMap<Integer, Integer>> getCurrenciesBasketState(){
		return atmTransactions.getCurrenciesBasketState();
	}
}
