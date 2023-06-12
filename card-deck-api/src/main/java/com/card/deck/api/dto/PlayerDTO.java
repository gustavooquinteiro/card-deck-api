package com.card.deck.api.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.card.deck.domain.model.Player;

public record PlayerDTO(Long id, List<CardDTO> cards, int hand_value) {

	public static PlayerDTO toDTO(Player player) {
		return new PlayerDTO(player.getPlayerId(), 
				player.getCards().stream()
					.map(CardDTO::toDTO)
					.collect(Collectors.toList()), 
				player.getPlayerHandValue());
	}
	
}
