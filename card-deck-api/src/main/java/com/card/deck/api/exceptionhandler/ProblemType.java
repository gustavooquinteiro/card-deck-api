package com.card.deck.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

	RESOURCE_NOT_FOUND("Resource not found", "/resource-not-found"),
	INSUFFICIENT_VALUES("Insufficient values", "/insufficient-values"),
	INSUFFICIENT_PLAYERS("Insufficient number of players", "/insufficient-players"),
	INSUFFICIENT_CARDS("Insufficient number of cards", "/insufficient-cards");
	
	private String title;
	private String uri;
	
	private ProblemType(String title, String uri) {
		this.title = title;
		this.uri = "https://my-card-deck.api.com" + uri;
	}
}
