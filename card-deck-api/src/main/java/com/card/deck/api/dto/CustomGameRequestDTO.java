package com.card.deck.api.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.card.deck.core.validation.SufficientPlayerQuantity;

public record CustomGameRequestDTO(
		@SufficientPlayerQuantity
		int player_quantity, 
		@NotNull
		List<PlayerDTO> players
		){
}
