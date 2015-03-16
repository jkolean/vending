package com.acme;

public enum VendingMessage {
	EXACT_CHANGE_ONLY("EXACT CHANGE ONLY"), INSERT_COIN("INSERT_COIN"), PRICE("PRICE $%.2f"), SOLD_OUT("SOLD OUT"), THANK_YOU("THANK YOU");
	String message;

	private VendingMessage(final String message) {
		this.message = message;
	}

	public String getDisplayMessage() {
		return message;
	}

	/**
	 * This is used for the price message to format the cost
	 *
	 * @param cost
	 * @return
	 */
	public String getDisplayMessage(final double cost) {
		return String.format(message, cost);
	}

}
