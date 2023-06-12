package com.card.deck.api.dto;

public record GameResponseDTO(Long game_id, String deck_id, int players, int cards_per_player) {

}
