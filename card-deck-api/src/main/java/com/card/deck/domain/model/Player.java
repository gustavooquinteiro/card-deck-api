package com.card.deck.domain.model;

public class Player {

	private Long playerId;
	private Hand playerHand;
	
	public Player() {
		this.playerHand = new Hand();
	}
	
	public Player(int handSize) {
		this.playerHand = new Hand(handSize);
	}
	
	public Long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
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
