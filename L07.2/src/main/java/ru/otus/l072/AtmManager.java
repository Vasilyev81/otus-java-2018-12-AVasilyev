package ru.otus.l072;

import ru.otus.l072.actions.PrintBalanceAction;
import ru.otus.l072.actions.*;
import ru.otus.l072.cashdrawer.CashDrawer;

public class AtmManager implements Runnable {
	public enum States {
		INIT, CURRENCY_CHOICE, ACTION_CHOICE, DEPOSIT, WITHDRAW, CURRENCY_BALANCE, BACKUP
	}

	private States state;
	private UserInterface ui;
	private CashDrawer cashDrawer;
	private Transaction transaction;
	private Utils utils;

	public AtmManager() {
		this.ui = new UserInterface();
		this.cashDrawer = CashDrawer.getCashDrawer();
		String currentCurrency = "";
		this.transaction = new Transaction(cashDrawer, currentCurrency);
		this.utils = new Utils(transaction);
	}

	public void initAndStart() {
		state = States.INIT;
		work();
	}

	private void work() {
		while (true) {
			switch (state) {
				case INIT: {
					state = perform(new InitAction(ui, cashDrawer, utils));
					break;
				}
				case CURRENCY_CHOICE: {
					state = perform(new CurrencyChoice(ui, transaction));
					break;
				}
				case ACTION_CHOICE: {
					state = perform(new TransactionChoice(ui));
					break;
				}
				case DEPOSIT: {
					state = perform(new DepositAction(ui, transaction));
					break;
				}
				case WITHDRAW: {
					state = perform(new WithdrawAction(ui, transaction));
					break;
				}
				case CURRENCY_BALANCE: {
					state = perform(new PrintBalanceAction(ui, cashDrawer));
					break;
				}
				case BACKUP: {
					state = perform(new BackupAction(state, transaction));
					break;
				}
			}
		}
	}

	private States perform(AbstractAction action) {
		return action.execute();
	}

	@Override
	public void run() {
		initAndStart();
	}
}

