package com.card.deck.domain.model;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Hand {
	
	public static int DEFAULT_HAND_SIZE = 5;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hand_id")
	private Long handId;
	@OneToMany(mappedBy = "hand", cascade = CascadeType.ALL)
	private List<Card> cards;
		
	public Long getHandId() {
		return handId;
	}

	public void setHandId(Long handId) {
		this.handId = handId;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		if (cards.size() <= DEFAULT_HAND_SIZE)
			this.cards = cards;
	}

	public int getHandValue() {
		return cards.stream()
				.collect(Collectors.summingInt(Card::getIntValue))
				.intValue();
	}

	public void addCard(Card card) {
		if (this.cards.size() + 1 <= DEFAULT_HAND_SIZE)
			this.cards.add(card);
	}
}
