package com.acme;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VendingMachineTest {

	@Test
	public void whenAcceptCoinIsPassed2NickelsCurrentValueIs10Cents() {
		final VendingMachine vendingMachine = new VendingMachine();

		vendingMachine.acceptCoin(Coin.NICKEL);
		vendingMachine.acceptCoin(Coin.NICKEL);

		assertEquals(10, vendingMachine.getAcceptedValue());
	}

	@Test
	public void whenAcceptCoinIsPassedNickelCurrentValueIs5Cents() {
		final VendingMachine vendingMachine = new VendingMachine();

		vendingMachine.acceptCoin(Coin.NICKEL);

		assertEquals(5, vendingMachine.getAcceptedValue());
	}

	@Test
	public void whenSelectProductIsPassedColaWithoutFundsItDisplaysPriceAndDoesNotDispense() {
		final VendingMachine vendingMachine = new VendingMachine();

		final String message = vendingMachine.selectProduct(Product.COLA);

		assertEquals("Price $1.00", message);
	}
}
