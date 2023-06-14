package com.card.deck.domain.exception;

public class DifferentPlayersQuantityException extends CardGameException {

	private static final long serialVersionUID = 1L;
	
	public static final String DEFAULT_MESSAGE = "The total amount of players must be the same as the parameter player_quantity";

	public DifferentPlayersQuantityException() {
		super(DEFAULT_MESSAGE);
	}
	
	public DifferentPlayersQuantityException(Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
	}

}
