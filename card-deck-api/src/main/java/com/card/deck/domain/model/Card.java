package com.card.deck.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Card {

	public static int ACE_VALUE = 1;
	public static int JACK_VALUE = 11;
	public static int QUEEN_VALUE = 12;
	public static int KING_VALUE = 13;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "card_id")
	private Long cardId;
	private String suit;
	@Column(name = "card_value")
	private String value;
	@ManyToOne
	@JoinColumn(name="hand_id")
	private Hand hand;
	
	public Card() {}
	
	public Card(String suit, String value) {
		setValue(value);
		setSuit(suit);
	}

	public int getIntValue() {
		return switch (getValue()) { 
		case "ACE" -> ACE_VALUE;
		case "JACK" -> JACK_VALUE;
		case "QUEEN" -> QUEEN_VALUE;
		case "KING" -> KING_VALUE;
		default -> Integer.parseInt(getValue());
		};
	}
	
	public Long getCardId() {
		return cardId;
	}

	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}

	public String getSuit() {
		return this.suit;
	}
	
	public void setSuit(String suit) {
		this.suit = suit;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
