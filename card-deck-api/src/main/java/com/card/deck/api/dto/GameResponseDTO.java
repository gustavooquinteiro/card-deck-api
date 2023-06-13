package com.card.deck.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "DTO for game response")
public record GameResponseDTO(
		@ApiModelProperty(notes = "Game ID")
		Long game_id,
		@ApiModelProperty(notes = "Deck ID from Deck of Cards API")
		String deck_id,
		@ApiModelProperty(notes = "Number of players")
		int players,
		@ApiModelProperty(notes = "Number of cards each player have")
		int cards_per_player) {

}
