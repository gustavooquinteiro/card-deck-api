package com.card.deck.domain.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.card.deck.api.dto.GameRequestDTO;
import com.card.deck.api.dto.GameResponseDTO;
import com.card.deck.api.dto.PlayerDTO;
import com.card.deck.api.dto.WinnerDTO;
import com.card.deck.domain.exception.GameNotFoundException;
import com.card.deck.domain.exception.InsufficientCardsException;
import com.card.deck.domain.exception.InsufficientPlayersException;
import com.card.deck.domain.model.Game;
import com.card.deck.domain.model.Hand;
import com.card.deck.domain.model.Player;
import com.card.deck.domain.repository.GameRepository;
@Service
public class GameService {

	private static final String GAME_DECK_NOT_FOUND_MESSAGE = "A game with deck %s was not found";
	private static final String GAME_ID_NOT_FOUND_MESSAGE = "A game with ID %d was not found";
	private static final int MINIMUM_PLAYER_QUANTITY = 2;
	private static final int MINIMUM_CARD_QUANTITY = 1;
	
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private HandService handService;
	
	@Autowired
	private ExternalCardApiService externalCardService;
	
	public Game createNewGame() {
		String deckId = externalCardService.retrieveDeck().getDeck_id();
		Game game = new Game(deckId);
		return gameRepository.save(game);
	}

	public Game dealCardsFromDeckToPlayers(int cardsQuantity, String deckId, int playersQuantity) {
		Game game = (deckId.isBlank())? createNewGame(): findGame(deckId);
		if (playersQuantity < MINIMUM_PLAYER_QUANTITY) throw new InsufficientPlayersException();
		if (cardsQuantity < MINIMUM_CARD_QUANTITY) throw new InsufficientCardsException();
		game.setPlayers(playersQuantity);
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
		
		List<PlayerDTO> playersDTO = (hasWinner) ? 
				List.of(PlayerDTO.toDTO(game.getWinner().get()))
				: retrievePlayersFromGame(gameId);
		return new WinnerDTO(hasWinner, isDraw, playersDTO);
	}
}