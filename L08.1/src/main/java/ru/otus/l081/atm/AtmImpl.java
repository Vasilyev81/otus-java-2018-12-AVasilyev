package ru.otus.l081.atm;

import org.apache.commons.lang3.RandomStringUtils;
import ru.otus.l081.atm.actions.*;
import ru.otus.l081.atm.cashdrawer.*;
import ru.otus.l081.atm.transactions.*;
import ru.otus.l081.userinterface.*;

import java.io.*;
import java.util.Arrays;
import java.util.Map;

public class AtmImpl implements Atm, Runnable {
	private final String ID;
	private AtmStates state;
	private UserInterface ui;
	private CashBox cashBox;
	private final Transaction transaction;

	public AtmImpl() {
		AtmUtil.init();
		ID = RandomStringUtils.randomAlphanumeric(10);
		this.ui = new UserInterfaceImpl();
		this.cashBox = new CashBox();
		Currency currentCurrency = Currency.RUR;
		this.transaction = new Transaction(cashBox, currentCurrency);
	}

	@Override
	public String getBalanceAsString() {
		return cashBox.getBalanceAsString();
	}

	@Override
	public void run() {
		initAndStart();
	}

	void initAndStart() {
		init();
		state = AtmStates.CURRENCY_CHOICE;
		work();
	}

	@Override
	public void init() {
		new InitAction(ui, cashBox).execute();
	}

	@Override
	public void start() {
		state = AtmStates.CURRENCY_CHOICE;
		work();
	}

	@Override
	public String getID() {
		return ID;
	}

	private void work() {
		boolean work = true;
		while (work) {
			AtmStates result = Arrays.stream(AtmStates.values()).filter(states -> states == state)
					.map(state -> state.make(ui, transaction)).findFirst().get().execute();
			if (result == null) {
				work = false;
			} else {
				this.state = result;
			}
		}
	}

	private class MementoImpl implements Memento {
		private AtmImpl atm;
		private byte[] snapshot;

		MementoImpl(AtmImpl atm) {
			this.atm = atm;
			snapshot = createSnapshot();
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
			objectOS.writeObject(this.cashBox);
			objectOS.flush();
			objectOS.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return byteOS.toByteArray();
	}

	private boolean restoreFromSnapshot(byte[] snapshot) {
		boolean result = false;
		CashBox cd = null;
				try {
			ObjectInputStream objectIS = new ObjectInputStream(new ByteArrayInputStream(snapshot));
			cd = (CashBox) objectIS.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		if (cd != null) {
			cashBox = cd;
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
		ui = new UserInterfaceImpl();
		return uiToReturn;
	}

	@Override
	public Memento getMemento() {
		return new MementoImpl(this);
	}
}

