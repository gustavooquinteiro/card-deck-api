package com.card.deck.api.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.card.deck.domain.model.Player;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "DTO for a player representation")
public record PlayerDTO(
		@ApiModelProperty(notes = "Player's ID")
		Long player_id,
		@ApiModelProperty(notes = "List of player's cards")
		List<CardDTO> player_cards,
		@ApiModelProperty(notes = "Player's hand value with the sum of all the cards' value")
		int player_hand_value) {

	public static PlayerDTO toDTO(Player player) {
		return new PlayerDTO(player.getPlayerId(), 
				player.getCards().stream()
					.map(CardDTO::toDTO)
					.collect(Collectors.toList()), 
				player.getPlayerHandValue());
	}
	
}
