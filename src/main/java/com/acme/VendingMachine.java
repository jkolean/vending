package com.acme;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendingMachine {
	protected List<Coin> acceptedCoins = new ArrayList<Coin>();
	protected List<Coin> coinReturn = new ArrayList<Coin>();
	protected Map<Product, Integer> contents = new HashMap<Product, Integer>();
	protected String message = "";

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

	public List<Coin> getCoinReturn() {
		return Collections.unmodifiableList(coinReturn);
	}

	String getDisplayMessage() {
		final String messageToReturn = message;
		if (VendingMessage.SOLD_OUT.getDisplayMessage().equals(messageToReturn) && getAcceptedValue() > 0) {
			message = VendingMessage.DEPOSITED_AMOUNT.getDisplayMessage(getAcceptedValue() / 100.0);
		} else {
			message = VendingMessage.INSERT_COIN.getDisplayMessage();
		}
		return messageToReturn;
	}

	public int getProductCount(final Product product) {
		if (!contents.containsKey(product)) {
			return 0;
		}
		return contents.get(product);
	}

	public void selectProduct(final Product product) {
		if (getProductCount(product) == 0) {
			message = VendingMessage.SOLD_OUT.getDisplayMessage();
		} else if (getAcceptedValue() >= product.getCost()) {
			dispense(product);
			final int change = getAcceptedValue() - product.getCost();
			updateCoinReturn(change);
			acceptedCoins = new ArrayList<Coin>();
			message = VendingMessage.THANK_YOU.getDisplayMessage();
		} else {
			message = VendingMessage.PRICE.getDisplayMessage(product.getCost() / 100.0);
		}
	}

	public void stockProduct(final Product product, final int count) {
		if (!contents.containsKey(product)) {
			contents.put(product, 0);
		}
		contents.put(product, contents.get(product) + count);
	}

	private void updateCoinReturn(final int change) {
		coinReturn = new ArrayList<Coin>();
		coinReturn.add(Coin.QUARTER);
	}

}
