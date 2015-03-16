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
	public void whenSelectProductIsPassedCandyWithoutFundsItDisplaysPriceAndDoesNotDispense() {
		final VendingMachine vendingMachine = new VendingMachine();
		final int preColaCount = vendingMachine.getProductCount(Product.CHIPS);

		final String message = vendingMachine.selectProduct(Product.CHIPS);

		assertEquals("Price $0.50", message);
		assertEquals(preColaCount, vendingMachine.getProductCount(Product.CHIPS));
	}

	@Test
	public void whenSelectProductIsPassedChipsWithoutFundsItDisplaysPriceAndDoesNotDispense() {
		final VendingMachine vendingMachine = new VendingMachine();
		final int preColaCount = vendingMachine.getProductCount(Product.CANDY);

		final String message = vendingMachine.selectProduct(Product.CANDY);

		assertEquals("Price $0.65", message);
		assertEquals(preColaCount, vendingMachine.getProductCount(Product.CANDY));
	}

	@Test
	public void whenSelectProductIsPassedColaWithExtraFundsItDisplaysThankYouDispensesAndReturnsChange() {
		final VendingMachine vendingMachine = new VendingMachine();
		vendingMachine.stockProduct(Product.COLA, 1);
		final int preColaCount = vendingMachine.getProductCount(Product.COLA);
		vendingMachine.acceptCoin(Coin.QUARTER);
		vendingMachine.acceptCoin(Coin.QUARTER);
		vendingMachine.acceptCoin(Coin.QUARTER);
		vendingMachine.acceptCoin(Coin.QUARTER);
		vendingMachine.acceptCoin(Coin.QUARTER);

		final String message = vendingMachine.selectProduct(Product.COLA);

		assertEquals("THANK YOU", message);
		assertEquals(preColaCount - 1, vendingMachine.getProductCount(Product.COLA));
		assertEquals(1, vendingMachine.getCoinReturn().size());
		assertEquals(Coin.QUARTER, vendingMachine.getCoinReturn().get(0));
	}

	@Test
	public void whenSelectProductIsPassedColaWithFundsItDisplaysThankYouAndDispense() {
		final VendingMachine vendingMachine = new VendingMachine();
		vendingMachine.stockProduct(Product.COLA, 1);
		final int preColaCount = vendingMachine.getProductCount(Product.COLA);
		vendingMachine.acceptCoin(Coin.QUARTER);
		vendingMachine.acceptCoin(Coin.QUARTER);
		vendingMachine.acceptCoin(Coin.QUARTER);
		vendingMachine.acceptCoin(Coin.QUARTER);

		final String message = vendingMachine.selectProduct(Product.COLA);

		assertEquals("THANK YOU", message);
		assertEquals(preColaCount - 1, vendingMachine.getProductCount(Product.COLA));
	}

	@Test
	public void whenSelectProductIsPassedColaWithoutFundsItDisplaysPriceAndDoesNotDispense() {
		final VendingMachine vendingMachine = new VendingMachine();
		final int preColaCount = vendingMachine.getProductCount(Product.COLA);

		final String message = vendingMachine.selectProduct(Product.COLA);

		assertEquals("Price $1.00", message);
		assertEquals(preColaCount, vendingMachine.getProductCount(Product.COLA));
	}
}
