package com.card.deck.api.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.card.deck.api.dto.CustomGameRequestDTO;
import com.card.deck.api.dto.GameRequestDTO;
import com.card.deck.api.dto.GameResponseDTO;
import com.card.deck.api.dto.PlayerDTO;
import com.card.deck.api.dto.WinnerDTO;
import com.card.deck.domain.exception.DifferentCardQuantityException;
import com.card.deck.domain.exception.DifferentPlayersQuantityException;
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
	@ResponseStatus(HttpStatus.CREATED)
	public GameResponseDTO startNewGame() { 
		return startNewGame(defaultConfiguration);
	}
	
	@Transactional
	@PostMapping("/new")
	@ApiOperation("Start a new game with a configuration passed by the user")
	@ResponseStatus(HttpStatus.CREATED)
	public GameResponseDTO startNewGame(@RequestBody @Valid GameRequestDTO gameConfig) {
		return gameService.setUpNewGame(gameConfig);
	}
	
	@Transactional
	@PostMapping("/custom/new")
	@ApiOperation("Start a new custom game with a configuration of all players and their cards passed by the user")
	@ResponseStatus(HttpStatus.CREATED)
	public GameResponseDTO startNewCustomGame(@RequestBody CustomGameRequestDTO gameConfig) {
		validate(gameConfig);
		return gameService.setUpNewCustomGame(gameConfig);
	}
	
	@GetMapping("/{gameId}/players")
	@ApiOperation("Get all the players from a given game ID")
	public List<PlayerDTO> retrievePlayers(@PathVariable Long gameId) {
		return gameService.retrievePlayersFromGame(gameId);
	}
	
	@GetMapping("/{gameId}/winner")
	@ApiOperation("Get a winner, a person with most card's points, or a list of all players if it's a draw")
	public WinnerDTO getWinnerFromGame(@PathVariable Long gameId) {
		return gameService.whoIsTheWinner(gameId);
	}
	
	private void validate(CustomGameRequestDTO gameConfig) {
		List<PlayerDTO> players = gameConfig.players();
		if (gameConfig.player_quantity() != players.size()) throw new DifferentPlayersQuantityException();
		int cardQuantity = players.get(0).player_cards().size();
		boolean sameSize = players.stream()
		    .allMatch(player -> player.player_cards().size() == cardQuantity);
		if (!sameSize) throw new DifferentCardQuantityException();		
	}
}
