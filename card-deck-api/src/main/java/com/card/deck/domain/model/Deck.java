package com.card.deck.domain.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Deck {

	private boolean success;
	private String deck_id;
	private boolean shuffled;
	private List<Card> cards;
	private int remaining;

}
