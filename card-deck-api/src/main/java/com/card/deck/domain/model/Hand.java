package com.card.deck.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Hand {
	
	private static int HAND_DEFAULT_SIZE = 5;
	private List<Card> cards;
	private int size;
	
	public Hand() {
		this.cards = new ArrayList<>(HAND_DEFAULT_SIZE);
		this.size = HAND_DEFAULT_SIZE;
	}
	
	public Hand(int size) {
		this.cards = new ArrayList<>(size);
		this.size = size;
	}
	
	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		if (cards.size() <= this.size)
			this.cards = cards;
	}

	public int getHandValue() {
		return cards.stream()
				.collect(Collectors.summingInt(Card::getValue))
				.intValue();
	}

	public void addCard(Card card) {
		if (this.cards.size() + 1 <= this.size)
			this.cards.add(card);
	}
}
