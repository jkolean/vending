package com.acme;

public enum Coin {
	NICKEL(5);
	int coinValue = 0;

	private Coin(final int coinValue) {
		this.coinValue = coinValue;
	}
}
