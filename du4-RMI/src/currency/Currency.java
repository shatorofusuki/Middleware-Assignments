package currency;

public enum Currency {
	EUR, USD, GBP;
	
	private double toDollar;
	
	static {
		USD.toDollar = 1.0;
		EUR.toDollar = 1.15746;
		GBP.toDollar = 1.31466;
	}
	
	public double PriceInUSD() {
		return toDollar;
	}
}
