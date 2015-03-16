package com.acme;

public enum Coin {
	DIME(10), NICKEL(5), QUARTER(25);
	int coinValue = 0;

	private Coin(final int coinValue) {
		this.coinValue = coinValue;
	}

	public int getCoinValue() {
		return coinValue;
	}
}
