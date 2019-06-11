package ru.otus.l081.atm;

import ru.otus.l081.atm.actions.*;
import ru.otus.l081.atm.cashdrawer.CashDrawer;
import ru.otus.l081.userinterface.AtmUserInterface;
import ru.otus.l081.userinterface.UserInterface;

public class AtmManager implements Runnable {


	//TODO: Pick out to the top level as explicit enum
	public enum States {
		INIT, CURRENCY_CHOICE, ACTION_CHOICE, DEPOSIT, WITHDRAW, CURRENCY_BALANCE, FINISH_WORK, BACKUP;
	}
	private States state;

	private UserInterface ui;
	private CashDrawer cashDrawer;
	private Transaction transaction;
	private Utils utils;
	public AtmManager() {
		this.ui = new AtmUserInterface();
		this.cashDrawer = CashDrawer.getCashDrawer();
		String currentCurrency = "";
		this.transaction = new Transaction(cashDrawer, currentCurrency);
		this.utils = new Utils(transaction);
	}

	public void initAndStart() {
		state = States.INIT;
		work();
	}

	public void  init() {state = States.INIT;}

	public void start() {work();}
	private void work() {
		boolean work = true;
		while (work) {
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
				case FINISH_WORK: {
					work = false;
					break;
				}
			}
		}
	}

	public void setUI(UserInterface departmentInterface) {
		ui = departmentInterface;
	}

	public void disconnectUI() {
		ui = null;
	}

	public UserInterface returnUI() {
		UserInterface toReturn = ui;
		ui = null;
		return toReturn;
	}

	private States perform(AbstractAction action) {
		return action.execute();
	}

	@Override
	public void run() {
		initAndStart();
	}
}

