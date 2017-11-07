package rmi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import currency.Currency;

public class ConverterServer extends UnicastRemoteObject implements ConverterInterface {

	private static final long serialVersionUID = 1L;

	protected ConverterServer() throws RemoteException {
		super();
	}

	@Override
	public double Convert(Currency from, Currency to, double amount) throws RemoteException {
		return amount*from.PriceInUSD()/to.PriceInUSD();
	}
	
	public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
 
            ConverterServer server = new ConverterServer();
            Naming.rebind("//0.0.0.0/Convert", server);
 
            System.out.println("Server started...");
 
        } catch (Exception e) {
            System.out.println("Error: " + e.getLocalizedMessage());
        }
 
    }
	
}
