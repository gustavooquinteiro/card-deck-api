package com.card.deck.domain.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long playerId;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "player_hand_id")
	private Hand playerHand;
	@ManyToOne
	@JoinColumn(name = "game_id")
	private Game game;
	
	public Player() { 
		this.playerHand = new Hand();
	}
			
	public int getPlayerHandValue() {
		return this.playerHand.getHandValue();
	}
	
	public List<Card> getCards(){
		return this.playerHand.getCards();
	}
}
