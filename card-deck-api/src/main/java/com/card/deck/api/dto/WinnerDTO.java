package com.card.deck.api.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "DTO for winner response")
public record WinnerDTO(
		@ApiModelProperty(notes = "Boolean that indicates if the game have or not a winner")
		boolean hasWinner,
		@ApiModelProperty(notes = "Boolean that indicates if the game it's a draw or not")
		boolean isDraw,
		@ApiModelProperty(notes = "List with the winner, if the game has a winner, or with all the players, if it's a draw")
		List<PlayerDTO> player) {

}
