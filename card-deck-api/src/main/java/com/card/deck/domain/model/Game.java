package com.card.deck.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Game {

	public static int DEFAULT_QUANTITY_OF_PLAYERS = 4;
	public static String DEFAULT_NEW_DECK = "";
	
	@Id
	@Column(name = "game_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long gameId;
	private String deckId; 
	@OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
	private List<Player> players;
	
	public Game() {
		this.players = new ArrayList<>();
	}
	
	public Game(String deckId) {
		this.deckId = deckId;
		this.players = new ArrayList<>();
	}
	
	public Optional<Player> getWinner() {
		 return this.players.stream()
	                .max((p1, p2) -> Integer.compare(p1.getPlayerHandValue(), p2.getPlayerHandValue()));	
	}	
}
