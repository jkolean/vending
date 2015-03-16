package com.acme;

public enum Product {
	CANDY(65), CHIPS(50), COLA(100);
	int cost = 0;

	private Product(final int cost) {
		this.cost = cost;
	}

	public int getCost() {
		return cost;
	}

}
