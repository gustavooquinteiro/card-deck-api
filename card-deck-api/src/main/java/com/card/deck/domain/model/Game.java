package com.card.deck.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Game {

	private Long gameId;
	private String deckId; 
	private List<Player> players;
	
	public Game() {
		this.players = new ArrayList<>();
	}
	
	public Game(String deckId) {
		this.deckId = deckId;
		this.players = new ArrayList<>();
	}
	
	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}

	public String getDeckId() {
		return deckId;
	}

	public void setDeckId(String deckId) {
		this.deckId = deckId;
	}

	public List<Player> getPlayers() {
		return players;
	}
	
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	
	public Player getWinner() {
		 return this.players.stream()
	                .max((p1, p2) -> Integer.compare(p1.getPlayerHandValue(), p2.getPlayerHandValue()))
	                .orElse(null);	
	}	
}
