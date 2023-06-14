package com.card.deck.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.card.deck.api.dto.CustomGameRequestDTO;
import com.card.deck.api.dto.GameRequestDTO;
import com.card.deck.api.dto.GameResponseDTO;
import com.card.deck.api.dto.PlayerDTO;
import com.card.deck.api.dto.WinnerDTO;
import com.card.deck.domain.exception.DifferentCardQuantityException;
import com.card.deck.domain.exception.DifferentPlayersQuantityException;
import com.card.deck.domain.exception.GameNotFoundException;
import com.card.deck.domain.model.Card;
import com.card.deck.domain.model.Game;
import com.card.deck.domain.model.Hand;
import com.card.deck.domain.model.Player;
import com.card.deck.domain.repository.GameRepository;
@Service
public class GameService {

	private static final String GAME_DECK_NOT_FOUND_MESSAGE = "A game with deck '%s' was not found";
	private static final String GAME_ID_NOT_FOUND_MESSAGE = "A game with ID %d was not found";
		
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private HandService handService;
	
	@Autowired
	private ExternalCardApiService externalCardService;
	
	public Game createNewGame(boolean custom) {
		String deckId = (custom)?"custom_game":externalCardService.retrieveDeck().getDeck_id();
		Game game = new Game(deckId);
		return gameRepository.save(game);
	}

	public Game dealCardsFromDeckToPlayers(int cardsQuantity, String deckId, int playersQuantity) {
		Game game = (deckId.isBlank())? createNewGame(false): findGame(deckId);
		List<Player> players = IntStream.range(0, playersQuantity)
			    .mapToObj(i -> {
			        Player player = new Player();
			        Hand hand = handService.saveHand(externalCardService.getCardsFromDeck(game.getDeckId(), cardsQuantity));
			        player.setPlayerHand(hand);
			        player.setGame(game);
			        return player;
			    })
			    .collect(Collectors.toList());
		game.setPlayers(players);
		return gameRepository.save(game);
	}
	
	public Game findGame(String deckId) {
		return gameRepository.findByDeckId(deckId)
				.orElseThrow(() -> new GameNotFoundException(
						String.format(GAME_DECK_NOT_FOUND_MESSAGE, deckId)));
	}
	
	private Game findGame(Long gameId) {
		return gameRepository.findById(gameId)
		.orElseThrow(() -> new GameNotFoundException(
				String.format(GAME_ID_NOT_FOUND_MESSAGE, gameId)));
	}
	
	public List<PlayerDTO> retrievePlayersFromGame(Long gameId){
		Game game = findGame(gameId);
		return game.getPlayers().stream()
				.map(PlayerDTO::toDTO)
				.collect(Collectors.toList());
	}

	public GameResponseDTO setUpNewGame(GameRequestDTO gameRequestDTO) {
		String deckId = gameRequestDTO.deck_id();
		Game game = dealCardsFromDeckToPlayers(gameRequestDTO.hand_size(), deckId, gameRequestDTO.player_quantity());
		return new GameResponseDTO(game.getGameId(), 
				game.getDeckId(), 
				game.getPlayers().size(), Hand.DEFAULT_HAND_SIZE);
	}

	public WinnerDTO whoIsTheWinner(Long gameId) {
		Game game = findGame(gameId);
		boolean hasWinner = game.getWinner().isPresent();
		boolean isDraw = !hasWinner;
		PlayerDTO winner = null;
		List<PlayerDTO> playersDTO = null;
		if (hasWinner)
			winner = PlayerDTO.toDTO(game.getWinner().get());
		else
			 playersDTO = retrievePlayersFromGame(gameId);
		
		return new WinnerDTO(hasWinner, isDraw, winner, playersDTO);
	}

	private Game setUp(CustomGameRequestDTO gameConfig) {
		validate(gameConfig);
		Game game = createNewGame(true);
		List<Player> players = new ArrayList<>(gameConfig.players().stream()
				.map(player -> {
					Player newPlayer = new Player();
					List<Card> newPlayersCards = new ArrayList<>(player.player_cards()
							.stream()
							.map(card -> new Card(card.suit(), card.value()))
							.toList()); 
					Hand hand = handService.saveHand(newPlayersCards);
					newPlayer.setPlayerHand(hand);
					newPlayer.setGame(game);
					return newPlayer;
				}).toList());
		game.setPlayers(players);		
		return gameRepository.save(game);
	}
	
	public GameResponseDTO setUpNewCustomGame(CustomGameRequestDTO gameConfig) {
		Game game = setUp(gameConfig);
		return new GameResponseDTO(game.getGameId(),game.getDeckId(), 
				game.getPlayers().size(), 
				game.getPlayers().get(0).getPlayerHand().getCards().size());
	}

	private void validate(CustomGameRequestDTO gameConfig) {
		if (gameConfig.player_quantity() != gameConfig.players().size()) throw new DifferentPlayersQuantityException();
		List<PlayerDTO> players = gameConfig.players();
		int cardQuantity = players.get(0).player_cards().size();
		boolean sameSize = players.stream()
		    .allMatch(player -> player.player_cards().size() == cardQuantity);
		if (!sameSize) throw new DifferentCardQuantityException();		
	}
}