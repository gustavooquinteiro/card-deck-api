package com.card.deck.api.dto;

import java.util.List;

public record WinnerDTO(boolean hasWinner, boolean isDraw, List<PlayerDTO> player) {

}
