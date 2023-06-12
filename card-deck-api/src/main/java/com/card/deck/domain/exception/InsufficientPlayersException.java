package com.card.deck.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InsufficientPlayersException extends CardGameException {

	private static final long serialVersionUID = 1L;
	
	private static final String DEFAULT_MESSAGE = "A card game should have more than 1 player";
	
	public InsufficientPlayersException() {
		super(DEFAULT_MESSAGE);
	}
	
	public InsufficientPlayersException(Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
	}

}
