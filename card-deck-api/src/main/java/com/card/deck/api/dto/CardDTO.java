package com.card.deck.api.dto;

import com.card.deck.domain.model.Card;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "DTO for a card representation")
public record CardDTO(
		@ApiModelProperty(notes = "Card's suit")
		String suit,
		@ApiModelProperty(notes = "Card's value")
		String value) {

	public static CardDTO toDTO(Card card) {
		return new CardDTO(card.getSuit(), card.getValue());
	}
}
