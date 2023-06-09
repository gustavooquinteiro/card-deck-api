package com.card.deck.domain.model;

public class Card {

	private static int ACE_VALUE = 1;
	private static int JESTER_VALUE = 11;
	private static int QUEEN_VALUE = 12;
	private static int KING_VALUE = 13;
	
	private String suit;
	private String rank;
	
	public Card(String suit, String rank) {
		setRank(rank);
		setSuit(suit);
	}

	public int getValue() {
		return switch (getRank()) { 
		case "A" -> ACE_VALUE;
		case "J" -> JESTER_VALUE;
		case "Q" -> QUEEN_VALUE;
		case "K" -> KING_VALUE;
		default -> Integer.parseInt(getRank());
		};
	}
	
	public String getSuit() {
		return this.suit;
	}
	
	public void setSuit(String suit) {
		this.suit = suit;
	}
	
	public String getRank() {
		return this.rank;
	}
	
	public void setRank(String rank) {
		this.rank = rank;
	}
}
