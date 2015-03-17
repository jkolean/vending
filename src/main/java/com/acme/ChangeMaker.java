package com.acme;

import java.util.ArrayList;
import java.util.List;

/**
 * This controls the change dispense module. Assume it is loaded with change by the stocker and is not replenished by the customer deposited coinage.
 *
 * @author jameskolean
 *
 */
public class ChangeMaker {
	int dimes = 0;
	int nickels = 0;
	int quarters = 0;

	public void add(final Coin coin, final int count) {
		switch (coin) {
		case QUARTER:
			quarters += count;
			break;
		case NICKEL:
			nickels += count;
			break;
		case DIME:
			dimes += count;
			break;
		}
	}

	/**
	 * Can we make change for this amount.
	 *
	 * @param amount
	 *            cents that we need to make change for
	 * @return true if we can make change for the amount
	 */
	public boolean canMakeChange(int amount) {
		int changeQuarters = 0;
		int changeDimes = 0;
		int changeNickels = 0;
		while (amount >= Coin.QUARTER.coinValue && quarters > changeQuarters) {
			amount -= Coin.QUARTER.coinValue;
			changeQuarters++;
		}
		while (amount >= Coin.DIME.coinValue && dimes > changeDimes) {
			amount -= Coin.DIME.coinValue;
			changeDimes--;
		}
		while (amount >= Coin.NICKEL.coinValue && nickels > changeNickels) {
			amount -= Coin.NICKEL.coinValue;
			changeNickels--;
		}
		return amount == 0;
	}

	/**
	 * Can we make change for this amount for at least one of the products.
	 *
	 * @param amount
	 *            cents that we need to make change for
	 * @param products
	 *            list of products we want to make change for
	 * @return true if we can make change for at least one product
	 */
	public boolean canMakeChange(final int amount, final Product[] products) {
		for (final Product product : products) {
			if (canMakeChange(amount - product.getCost())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Dispense change for the amount
	 *
	 * @param amount
	 *            cents that we need to make change for
	 * @return change
	 */
	public List<Coin> makeChange(int amount) {
		final List<Coin> result = new ArrayList<Coin>();
		while (amount >= Coin.QUARTER.coinValue && quarters > 0) {
			amount -= Coin.QUARTER.coinValue;
			quarters--;
			result.add(Coin.QUARTER);
		}
		while (amount >= Coin.DIME.coinValue && dimes > 0) {
			amount -= Coin.DIME.coinValue;
			dimes--;
			result.add(Coin.DIME);
		}
		while (amount >= Coin.NICKEL.coinValue && nickels > 0) {
			amount -= Coin.NICKEL.coinValue;
			nickels--;
			result.add(Coin.NICKEL);
		}

		return result;
	}
}
