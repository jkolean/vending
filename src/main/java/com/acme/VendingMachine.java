package com.acme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VendingMachine {
	ArrayList<Coin> acceptedCoins = new ArrayList<Coin>();
	Map<Product, Integer> contents = new HashMap<Product, Integer>();

	public void acceptCoin(final Coin nickel) {
		acceptedCoins.add(nickel);
	}

	private void dispense(final Product product) {
		if (!contents.containsKey(product)) {
			return;
		}
		contents.put(product, contents.get(product) - 1);
	}

	public int getAcceptedValue() {
		int value = 0;
		for (final Coin coin : acceptedCoins) {
			value += coin.getCoinValue();
		}
		return value;
	}

	public int getProductCount(final Product product) {
		if (!contents.containsKey(product)) {
			return 0;
		}
		return contents.get(product);
	}

	public String selectProduct(final Product product) {
		if (getAcceptedValue() >= product.getCost()) {
			dispense(product);
			return "THANK YOU";
		} else {
			return String.format("Price $%.2f", product.getCost() / 100.0);
		}
	}

	public void stockProduct(final Product product, final int count) {
		if (!contents.containsKey(product)) {
			contents.put(product, 0);
		}
		contents.put(product, contents.get(product) + count);
	}

}
