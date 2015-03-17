package com.acme;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class VendingMachineTest {

	private VendingMachine vendingMachine;

	@Test
	public void displayMessageAllStateTransitions() {
		vendingMachine.loadChange(Coin.QUARTER, 1);
		assertEquals(VendingMessage.INSERT_COIN.message, vendingMachine.getDisplayMessage());

		vendingMachine.acceptCoin(Coin.QUARTER);
		vendingMachine.acceptCoin(Coin.QUARTER);
		assertEquals(VendingMessage.INSERT_COIN.message, vendingMachine.getDisplayMessage());

		vendingMachine.acceptCoin(Coin.DIME);
		vendingMachine.acceptCoin(Coin.DIME);
		// can't make change for 70 cents
		assertEquals(VendingMessage.EXACT_CHANGE_ONLY.message, vendingMachine.getDisplayMessage());

		vendingMachine.acceptCoin(Coin.NICKEL);
		assertEquals(VendingMessage.INSERT_COIN.message, vendingMachine.getDisplayMessage());

		vendingMachine.selectProduct(Product.CANDY);
		assertEquals(VendingMessage.SOLD_OUT.message, vendingMachine.getDisplayMessage());
		assertEquals("$0.75", vendingMachine.getDisplayMessage());
		assertEquals(VendingMessage.INSERT_COIN.message, vendingMachine.getDisplayMessage());

		vendingMachine.stockProduct(Product.COLA, 1);
		vendingMachine.selectProduct(Product.COLA);
		assertEquals("PRICE $1.00", vendingMachine.getDisplayMessage());
		assertEquals(VendingMessage.INSERT_COIN.message, vendingMachine.getDisplayMessage());

		vendingMachine.acceptCoin(Coin.QUARTER);
		vendingMachine.selectProduct(Product.COLA);
		assertEquals(VendingMessage.THANK_YOU.message, vendingMachine.getDisplayMessage());
		assertEquals(VendingMessage.INSERT_COIN.message, vendingMachine.getDisplayMessage());
	}

	@Before
	public void setup() {
		vendingMachine = new VendingMachine();
	}

	@Test
	public void whenAcceptCoinIsPassed2NickelsCurrentValueIs10Cents() {

		vendingMachine.acceptCoin(Coin.NICKEL);
		vendingMachine.acceptCoin(Coin.NICKEL);

		assertEquals(10, vendingMachine.getAcceptedValue());
	}

	@Test
	public void whenAcceptCoinIsPassedNickelCurrentValueIs5Cents() {

		vendingMachine.acceptCoin(Coin.NICKEL);

		assertEquals(5, vendingMachine.getAcceptedValue());
	}

	@Test
	public void whenAcceptCoinIsPassedQuarterDimeAndNickeCurrentValueIs40Cents() {

		vendingMachine.acceptCoin(Coin.QUARTER);
		vendingMachine.acceptCoin(Coin.DIME);
		vendingMachine.acceptCoin(Coin.NICKEL);

		assertEquals(40, vendingMachine.getAcceptedValue());
	}

	@Test
	public void whenReturnCoinsIsCalledCoinsAreReturnedAndMessageIsUpdated() {
		vendingMachine.acceptCoin(Coin.QUARTER);
		vendingMachine.acceptCoin(Coin.NICKEL);
		vendingMachine.acceptCoin(Coin.DIME);

		vendingMachine.returnCoins();

		assertEquals("INSERT COIN", vendingMachine.getDisplayMessage());
		assertEquals("INSERT COIN", vendingMachine.getDisplayMessage());
		assertEquals(3, vendingMachine.getCoinReturn().size());
		assertTrue("Expected a quarter", vendingMachine.getCoinReturn().contains(Coin.QUARTER));
		assertTrue("Expected a nickel", vendingMachine.getCoinReturn().contains(Coin.NICKEL));
		assertTrue("Expected a dime", vendingMachine.getCoinReturn().contains(Coin.DIME));
		assertEquals(0, vendingMachine.getAcceptedValue());
	}

	@Test
	public void whenSelectProductIsPassedCandyWithoutFundsItDisplaysPriceAndDoesNotDispense() {
		vendingMachine.stockProduct(Product.CHIPS, 1);
		final int preColaCount = vendingMachine.getProductCount(Product.CHIPS);

		vendingMachine.selectProduct(Product.CHIPS);

		assertEquals("PRICE $0.50", vendingMachine.getDisplayMessage());
		assertEquals("INSERT COIN", vendingMachine.getDisplayMessage());
		assertEquals(preColaCount, vendingMachine.getProductCount(Product.CHIPS));
	}

	@Test
	public void whenSelectProductIsPassedChipsWithoutFundsItDisplaysPriceAndDoesNotDispense() {
		vendingMachine.stockProduct(Product.CANDY, 1);
		final int preColaCount = vendingMachine.getProductCount(Product.CANDY);

		vendingMachine.selectProduct(Product.CANDY);

		assertEquals("PRICE $0.65", vendingMachine.getDisplayMessage());
		assertEquals("INSERT COIN", vendingMachine.getDisplayMessage());
		assertEquals(preColaCount, vendingMachine.getProductCount(Product.CANDY));
	}

	@Test
	public void whenSelectProductIsPassedColaWithDepositWithoutStockItDisplaysSoldOutThenDepositAmount() {
		final int preColaCount = vendingMachine.getProductCount(Product.COLA);
		vendingMachine.acceptCoin(Coin.QUARTER);

		vendingMachine.selectProduct(Product.COLA);

		assertEquals("SOLD OUT", vendingMachine.getDisplayMessage());
		assertEquals("$0.25", vendingMachine.getDisplayMessage());
		assertEquals(preColaCount, vendingMachine.getProductCount(Product.COLA));
	}

	@Test
	public void whenSelectProductIsPassedColaWithExtraFundsItDisplaysThankYouDispensesAndReturnsChange() {
		vendingMachine.loadChange(Coin.QUARTER, 1);
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
		vendingMachine.stockProduct(Product.COLA, 1);
		final int preColaCount = vendingMachine.getProductCount(Product.COLA);

		vendingMachine.selectProduct(Product.COLA);

		assertEquals("PRICE $1.00", vendingMachine.getDisplayMessage());
		assertEquals(preColaCount, vendingMachine.getProductCount(Product.COLA));
	}

	@Test
	public void whenSelectProductIsPassedColaWithoutStockItDisplaysSoldOut() {
		final int preColaCount = vendingMachine.getProductCount(Product.COLA);

		vendingMachine.selectProduct(Product.COLA);

		assertEquals("SOLD OUT", vendingMachine.getDisplayMessage());
		assertEquals("INSERT COIN", vendingMachine.getDisplayMessage());
		assertEquals(preColaCount, vendingMachine.getProductCount(Product.COLA));
	}

	@Test
	public void whenWeCanNoMakeChangeThenDisplayExactChange() {
		vendingMachine.acceptCoin(Coin.QUARTER);
		vendingMachine.acceptCoin(Coin.QUARTER);
		vendingMachine.acceptCoin(Coin.DIME);
		vendingMachine.acceptCoin(Coin.DIME);

		assertEquals(VendingMessage.EXACT_CHANGE_ONLY.message, vendingMachine.getDisplayMessage());
	}

}
