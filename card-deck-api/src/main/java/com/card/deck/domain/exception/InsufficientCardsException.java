package com.card.deck.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InsufficientCardsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public static final String DEFAULT_MESSAGE = "A card game should have a positive non-zero number of cards in a hand";
	
	public InsufficientCardsException() {
		super(DEFAULT_MESSAGE);
	}
	
	public InsufficientCardsException(Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
	}

}
