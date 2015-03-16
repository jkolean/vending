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
		vendingMachine.stockProduct(Product.CHIPS, 1);
		final int preColaCount = vendingMachine.getProductCount(Product.CHIPS);

		vendingMachine.selectProduct(Product.CHIPS);

		assertEquals("PRICE $0.50", vendingMachine.getDisplayMessage());
		assertEquals("INSERT COIN", vendingMachine.getDisplayMessage());
		assertEquals(preColaCount, vendingMachine.getProductCount(Product.CHIPS));
	}

	@Test
	public void whenSelectProductIsPassedChipsWithoutFundsItDisplaysPriceAndDoesNotDispense() {
		final VendingMachine vendingMachine = new VendingMachine();
		vendingMachine.stockProduct(Product.CANDY, 1);
		final int preColaCount = vendingMachine.getProductCount(Product.CANDY);

		vendingMachine.selectProduct(Product.CANDY);

		assertEquals("PRICE $0.65", vendingMachine.getDisplayMessage());
		assertEquals("INSERT COIN", vendingMachine.getDisplayMessage());
		assertEquals(preColaCount, vendingMachine.getProductCount(Product.CANDY));
	}

	@Test
	public void whenSelectProductIsPassedColaWithDepositWithoutStockItDisplaysSoldOutThenDepositAmount() {
		final VendingMachine vendingMachine = new VendingMachine();
		final int preColaCount = vendingMachine.getProductCount(Product.COLA);
		vendingMachine.acceptCoin(Coin.QUARTER);

		vendingMachine.selectProduct(Product.COLA);

		assertEquals("SOLD OUT", vendingMachine.getDisplayMessage());
		assertEquals("$0.25", vendingMachine.getDisplayMessage());
		assertEquals(preColaCount, vendingMachine.getProductCount(Product.COLA));
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

		vendingMachine.selectProduct(Product.COLA);

		assertEquals("THANK YOU", vendingMachine.getDisplayMessage());
		assertEquals("INSERT COIN", vendingMachine.getDisplayMessage());
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

		vendingMachine.selectProduct(Product.COLA);

		assertEquals("THANK YOU", vendingMachine.getDisplayMessage());
		assertEquals("INSERT COIN", vendingMachine.getDisplayMessage());
		assertEquals(preColaCount - 1, vendingMachine.getProductCount(Product.COLA));
	}

	@Test
	public void whenSelectProductIsPassedColaWithoutFundsItDisplaysPriceAndDoesNotDispense() {
		final VendingMachine vendingMachine = new VendingMachine();
		vendingMachine.stockProduct(Product.COLA, 1);
		final int preColaCount = vendingMachine.getProductCount(Product.COLA);

		vendingMachine.selectProduct(Product.COLA);

		assertEquals("PRICE $1.00", vendingMachine.getDisplayMessage());
		assertEquals(preColaCount, vendingMachine.getProductCount(Product.COLA));
	}

	@Test
	public void whenSelectProductIsPassedColaWithoutStockItDisplaysSoldOut() {
		final VendingMachine vendingMachine = new VendingMachine();
		final int preColaCount = vendingMachine.getProductCount(Product.COLA);

		vendingMachine.selectProduct(Product.COLA);

		assertEquals("SOLD OUT", vendingMachine.getDisplayMessage());
		assertEquals("INSERT COIN", vendingMachine.getDisplayMessage());
		assertEquals(preColaCount, vendingMachine.getProductCount(Product.COLA));
	}
}
