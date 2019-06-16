package ru.otus.l081.atm;

import ru.otus.l081.atm.actions.*;
import ru.otus.l081.atm.transactions.Transaction;
import ru.otus.l081.userinterface.UserInterface;

public enum AtmStates {
	CURRENCY_CHOICE {
		@Override
		AbstractAction make(UserInterface ui, Transaction transaction) {
			return new SelectCurrencyAction(ui, transaction);
		}
	},
	ACTION_CHOICE {
		@Override
		AbstractAction make(UserInterface ui, Transaction transaction) {
			return new SelectTransactionAction(ui);
		}
	},
	DEPOSIT {
		@Override
		AbstractAction make(UserInterface ui, Transaction transaction) {
			return new DepositAction(ui, transaction);
		}
	},
	WITHDRAW {
		@Override
		AbstractAction make(UserInterface ui, Transaction transaction) {
			return new WithdrawAction(ui, transaction);
		}
	},
	CURRENCY_BALANCE {
		@Override
		AbstractAction make(UserInterface ui, Transaction transaction) {
			return new PrintBalanceAction(ui, transaction);
		}
	},
	FINISH_WORK {
		@Override
		AbstractAction make(UserInterface ui, Transaction transaction) {
			return new StopAction();
		}
	};

	abstract AbstractAction make(UserInterface ui, Transaction transaction);
}