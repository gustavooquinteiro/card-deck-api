package com.card.deck.api.dto;

import java.util.List;

public record CustomGameRequestDTO(
		int player_quantity, 
		List<PlayerDTO> players
		){
}
