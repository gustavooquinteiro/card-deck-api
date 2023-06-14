package com.card.deck.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

	RESOURCE_NOT_FOUND("Resource not found", "/resource-not-found"),
	METHOD_NOT_ALLOWED("Method not allowed", "/method-not-allowed"),
	INSUFFICIENT_VALUES("Insufficient values", "/insufficient-values"),
	INSUFFICIENT_PLAYERS("Insufficient number of players", "/insufficient-players"),
	INSUFFICIENT_CARDS("Insufficient number of cards", "/insufficient-cards"), 
	DIFFERENT_CARD_AMOUNT("Different card amount", "/different-card-amount"), 
	DIFFERENT_PLAYER_AMOUNT("Different player amount", "/different-player-amount");
	
	private String title;
	private String uri;
	
	private ProblemType(String title, String uri) {
		this.title = title;
		this.uri = "https://my-card-deck.api.com" + uri;
	}
}
