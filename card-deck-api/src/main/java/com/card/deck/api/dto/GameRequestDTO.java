package com.card.deck.api.dto;

import com.card.deck.core.validation.SufficientCardQuantity;
import com.card.deck.core.validation.SufficientPlayerQuantity;

public record GameRequestDTO(
		@SufficientPlayerQuantity int player_quantity, 
		@SufficientCardQuantity int hand_size, 
		String deck_id) {
}
