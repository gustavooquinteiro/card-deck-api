package com.card.deck.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GameDeckNotFoundException extends CardGameException{

	private static final long serialVersionUID = 1L;
	
	public GameDeckNotFoundException(String message) {
		super(message);
	}
	
	public GameDeckNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
