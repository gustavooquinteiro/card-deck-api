package com.card.deck.api.dto;

import com.card.deck.core.validation.SufficientCardQuantity;
import com.card.deck.core.validation.SufficientPlayerQuantity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "DTO for game request")
public record GameRequestDTO(
		@ApiModelProperty(notes = "Quantity of players that will play.")
		@SufficientPlayerQuantity 
		int player_quantity, 
		@ApiModelProperty(notes = "Quantity of cards that each player will have.")
		@SufficientCardQuantity 
		int hand_size,
		@ApiModelProperty(notes = "ID of a deck from Deck of Cards API. Let it blank to retrieve new deck from Deck of Cards API.")
		String deck_id) {
}
