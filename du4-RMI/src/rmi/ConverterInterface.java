package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import currency.Currency;

public interface ConverterInterface extends Remote {
	public double Convert(Currency from, Currency to, double amount) throws RemoteException;
}
