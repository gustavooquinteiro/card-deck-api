package com.card.deck.api.dto;

import com.card.deck.domain.model.Card;

public record CardDTO(String suit, String value) {

	public static CardDTO toDTO(Card card) {
		return new CardDTO(card.getSuit(), card.getValue());
	}
}
