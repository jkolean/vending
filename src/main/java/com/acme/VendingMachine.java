package com.acme;

import java.util.ArrayList;

public class VendingMachine {
	ArrayList<Coin> acceptedCoins = new ArrayList<Coin>();

	public void acceptCoin(final Coin nickel) {
		acceptedCoins.add(nickel);
	}

	public int getAcceptedValue() {
		int value = 0;
		for (final Coin coin : acceptedCoins) {
			value += coin.getCoinValue();
		}
		return value;
	}

}