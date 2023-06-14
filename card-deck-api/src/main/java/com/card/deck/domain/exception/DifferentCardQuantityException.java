package com.card.deck.domain.exception;

public class DifferentCardQuantityException extends CardGameException {

	private static final long serialVersionUID = 1L;
	
	public static final String DEFAULT_MESSAGE = "All hands must have the same amount of cards";
	
	public DifferentCardQuantityException() {
		super(DEFAULT_MESSAGE);
	}
	
	public DifferentCardQuantityException(Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
	}

}
