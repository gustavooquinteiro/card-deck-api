package com.card.deck.api.dto;

import java.util.List;

public record DeckResponseExternalApiDTO(boolean success, String deck_id, List<CardResponseExternalApiDTO> cards,String remaining) {

}
