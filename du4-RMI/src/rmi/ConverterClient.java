package rmi;

import java.rmi.Naming;

import currency.Currency;

public class ConverterClient {
	public static void main(String[] args) throws Exception{
        ConverterInterface client = (ConverterInterface)Naming.lookup("//localhost/Convert");
        
        System.out.println(client.Convert(Currency.EUR, Currency.GBP, 123.45)); // 108.69 British Pound
        System.out.println(client.Convert(Currency.GBP, Currency.EUR, 123.45)); // 140.22 Euro
    }
}
