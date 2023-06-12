package com.card.deck.domain.exception;

public class CardGameException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public CardGameException(String message) {
		super(message);
	}

	public CardGameException(String message, Throwable cause) {
		super(message, cause);
	}

}
