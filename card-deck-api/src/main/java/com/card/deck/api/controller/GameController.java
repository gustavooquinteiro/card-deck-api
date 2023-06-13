package com.card.deck.api.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/game")
@Api(tags = "Game")
public class GameController {

	@Autowired
	private GameService gameService;
		
	private final GameRequestDTO defaultConfiguration = new GameRequestDTO(
			Game.DEFAULT_QUANTITY_OF_PLAYERS, 
			Hand.DEFAULT_HAND_SIZE, 
			Game.DEFAULT_NEW_DECK);  
	
	@Transactional
	@GetMapping("/new")
	@ApiOperation("Start a new game with a default configuration: 4 players and 5 cards each of a new shuffled random deck from Deck of Cards API")
	public GameResponseDTO startNewGame() { 
		return startNewGame(defaultConfiguration);
	}
	
	@Transactional
	@PostMapping("/new")
	@ApiOperation("Start a new game with a configuration passed by the user")
	public GameResponseDTO startNewGame(@RequestBody @Valid GameRequestDTO gameConfig) {
		return gameService.setUpNewGame(gameConfig);
	}
	
	@GetMapping("/{gameId}/winner")
	@ApiOperation("Get a winner, a person with most card's points, or a list of all players if it's a draw")
	public WinnerDTO getWinnerFromGame(@PathVariable Long gameId) {
		return gameService.whoIsTheWinner(gameId);
	}
}
