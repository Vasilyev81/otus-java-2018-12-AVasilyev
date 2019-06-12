package ru.otus.l081.atm;

import ru.otus.l081.atm.actions.*;
import ru.otus.l081.atm.cashdrawer.CashDrawer;
import ru.otus.l081.atm.transactions.AtmMementoInterface;
import ru.otus.l081.atm.transactions.Transaction;
import ru.otus.l081.userinterface.AtmUserInterface;
import ru.otus.l081.userinterface.UserInterface;

import java.io.*;

public class Atm implements AtmInterface, Runnable {
	private States state;

	//TODO: Pick out to the top level as explicit enum
	public enum States {
		CURRENCY_CHOICE,
		ACTION_CHOICE,
		DEPOSIT,
		WITHDRAW,
		CURRENCY_BALANCE,
		FINISH_WORK,
		BACKUP
	}

	private UserInterface ui;
	private CashDrawer cashDrawer;
	private Transaction transaction;
	private Utils utils;

	public Atm() {
		this.ui = new AtmUserInterface();
		this.cashDrawer = new CashDrawer();
		String currentCurrency = "";
		this.transaction = new Transaction(cashDrawer, currentCurrency);
		this.utils = new Utils(transaction);
	}

	@Override
	public AtmMementoInterface getMemento() {
		return new Memento(this);
	}

	@Override
	public String getBalance() {
		return cashDrawer.getBalance();
	}

	@Override
	public void run() {
		initAndStart();
	}

	void initAndStart() {
		init();
		state = States.CURRENCY_CHOICE;
		work();
	}

	@Override
	public void init() {
		new InitAction(ui, cashDrawer, utils).execute();
	}

	@Override
	public void start() {
		state = States.CURRENCY_CHOICE;
		work();
	}

	private void work() {
		boolean work = true;
		while (work) {
			switch (state) {
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

	private States perform(AbstractAction action) {
		return action.execute();
	}

	private class Memento implements AtmMementoInterface {
		private Atm atm;
		private byte[] snapshot;

		Memento(Atm atm) {
			this.atm = atm;
			snapshot = atm.createSnapshot();
		}

		@Override
		public boolean restore() {
			return atm.restoreFromSnapshot(snapshot);
		}
	}

	private byte[] createSnapshot() {
		ByteArrayOutputStream byteOS = new ByteArrayOutputStream();
		ObjectOutputStream objectOS;
		try {
			objectOS = new ObjectOutputStream(byteOS);
			objectOS.writeObject(this.cashDrawer);
			objectOS.flush();
			objectOS.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return byteOS.toByteArray();
	}

	private boolean restoreFromSnapshot(byte[] snapshot) {
		boolean result = false;
		CashDrawer cd = null;
		try {
			ObjectInputStream objectIS = new ObjectInputStream(new ByteArrayInputStream(snapshot));
			cd = (CashDrawer) objectIS.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		if (cd != null) {
			cashDrawer = cd;
			result = true;
		}
		return result;
	}

	@Override
	public void hookUI(UserInterface ui) {
		this.ui = ui;
	}

	@Override
	public UserInterface unhookUI() {
		UserInterface uiToReturn = ui;
		ui = new AtmUserInterface();
		return uiToReturn;
	}
}

