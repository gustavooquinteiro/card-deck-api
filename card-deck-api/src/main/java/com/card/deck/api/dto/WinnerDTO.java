package com.card.deck.api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "DTO for winner response")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record WinnerDTO(
		@ApiModelProperty(notes = "Boolean that indicates if the game have or not a winner")
		boolean hasWinner,
		@ApiModelProperty(notes = "Boolean that indicates if the game it's a draw or not")
		boolean isDraw,
		@ApiModelProperty(notes = "The winner of a game")
		PlayerDTO winner,
		@ApiModelProperty(notes = "List with all the players, if the game it's a draw")
		List<PlayerDTO> players) {

}
