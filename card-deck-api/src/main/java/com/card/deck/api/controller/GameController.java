package com.card.deck.api.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.card.deck.api.dto.GameRequestDTO;
import com.card.deck.api.dto.GameResponseDTO;
import com.card.deck.api.dto.WinnerDTO;
import com.card.deck.domain.model.Game;
import com.card.deck.domain.model.Hand;
import com.card.deck.domain.service.GameService;

@RestController
@RequestMapping("/game")
public class GameController {

	@Autowired
	private GameService gameService;
	
	private final GameRequestDTO defaultConfiguration = new GameRequestDTO(
			Game.DEFAULT_QUANTITY_OF_PLAYERS, 
			Hand.DEFAULT_HAND_SIZE, 
			Game.DEFAULT_NEW_DECK);  
	
	@Transactional
	@GetMapping("/new")
	public GameResponseDTO startNewGame() { 
		return startNewGame(defaultConfiguration);
	}
	
	@Transactional
	@PostMapping("/new")
	public GameResponseDTO startNewGame(@RequestBody GameRequestDTO gameConfig) {
		return gameService.setUpNewGame(gameConfig);
	}
	
	@GetMapping("{gameId}/winner")
	public WinnerDTO getWinnerFromGame(@PathVariable Long gameId) {
		return gameService.whoIsTheWinner(gameId);
	}
}
