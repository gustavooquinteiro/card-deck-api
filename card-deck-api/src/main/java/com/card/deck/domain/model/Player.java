package com.card.deck.domain.model;

public class Player {

	private Hand playerHand;
	
	public Player() {
		this.playerHand = new Hand();
	}
	
	public void setPlayerHand(Hand hand) {
		this.playerHand = hand;
	}
	
	public Hand getPlayerHand() {
		return this.playerHand;
	}
	
	public int getPlayerHandValue() {
		return this.playerHand.getHandValue();
	}
}
