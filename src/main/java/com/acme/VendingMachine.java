package com.acme;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is the brains of a vending machine and has the following core capabilities:<br/>
 * <br/>
 * The vending machine may be stocked with products using stockProduct() and coins for the change return are loaded using loadChange() <br/>
 * <br/>
 * Coins can be inserted into the the machine with acceptCoin() and the current value of all accepted coins can be accessed with getAcceptedValue() <br/>
 * <br/>
 * Users can choose a product to purchase using selectProduct <br/>
 * <br/>
 * The message displayed by the machine is accessed with getDisplayMessage(). Note that accessing this method changes the state machine of the message.
 * Therefore, calling it twice in a row may yield different results.
 *
 * @author jameskolean
 *
 */
public class VendingMachine {
	protected List<Coin> acceptedCoins = new ArrayList<Coin>();
	protected ChangeMaker changeMaker = new ChangeMaker();
	protected List<Coin> coinReturn = new ArrayList<Coin>();
	protected Map<Product, Integer> contents = new HashMap<Product, Integer>();
	protected String message = "";

	/**
	 * User has entered a coin
	 *
	 * @param coin
	 */
	public void acceptCoin(final Coin coin) {
		acceptedCoins.add(coin);
	}

	/**
	 * decrement product and lazy initialize the product count
	 *
	 * @param product
	 */
	private void dispense(final Product product) {
		if (!contents.containsKey(product)) {
			return;
		}
		contents.put(product, contents.get(product) - 1);
	}

	/**
	 * What's the value of the coins the user has entered
	 *
	 * @return value of coins entered
	 */
	protected int getAcceptedValue() {
		int value = 0;
		for (final Coin coin : acceptedCoins) {
			value += coin.getCoinValue();
		}
		return value;
	}

	public List<Coin> getCoinReturn() {
		return Collections.unmodifiableList(coinReturn);
	}

	/**
	 * State machine for the display message. Calls to the method will transition the state
	 *
	 * @return last message
	 */
	public String getDisplayMessage() {
		final String messageToReturn = "".equals(message) ? VendingMessage.INSERT_COIN.getDisplayMessage() : message;
		if (VendingMessage.SOLD_OUT.getDisplayMessage().equals(messageToReturn) && getAcceptedValue() > 0) {
			message = VendingMessage.DEPOSITED_AMOUNT.getDisplayMessage(getAcceptedValue() / 100.0);
		} else {
			message = VendingMessage.INSERT_COIN.getDisplayMessage();
		}
		if (VendingMessage.INSERT_COIN.getDisplayMessage().equals(messageToReturn) && getAcceptedValue() > Product.CHIPS.getCost()
				&& !changeMaker.canMakeChange(getAcceptedValue(), new Product[] { Product.CANDY, Product.CHIPS, Product.COLA })) {
			return VendingMessage.EXACT_CHANGE_ONLY.getDisplayMessage();
		}
		return messageToReturn;
	}

	protected int getProductCount(final Product product) {
		if (!contents.containsKey(product)) {
			return 0;
		}
		return contents.get(product);
	}

	public void loadChange(final Coin coin, final int count) {
		changeMaker.add(coin, count);
	}

	protected void returnCoins() {
		message = VendingMessage.INSERT_COIN.getDisplayMessage();
		coinReturn = acceptedCoins;
		acceptedCoins = new ArrayList<Coin>();
	}

	/**
	 * User chooses a product they want
	 * 
	 * @param product
	 */
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

	/**
	 * Stock person adds product to the vending machine
	 * 
	 * @param product
	 * @param count
	 */
	public void stockProduct(final Product product, final int count) {
		if (!contents.containsKey(product)) {
			contents.put(product, 0);
		}
		contents.put(product, contents.get(product) + count);
	}

	private void updateCoinReturn(final int change) {
		coinReturn = changeMaker.makeChange(change);
	}

}
