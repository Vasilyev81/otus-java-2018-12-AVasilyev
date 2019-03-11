package ru.otus.l071.atm;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

public class ATMDepartmentServer extends UnicastRemoteObject implements ATMRemote throws RemoteException {

	public ATMDepartmentServer throws RemoteException (){
		super();
		}

	@Override
	public Map<String, Map<Integer, Integer>> getCurrenciesBusketState() throws RemoteException {
		return null;
	}

	@Override
	public void setCurrenciesBusketState(Map<String, Map<Integer, Integer>> state) throws RemoteException {

	}
}
