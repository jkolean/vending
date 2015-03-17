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
	public boolean canMakeChange(final int amount) {
		return true;
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
