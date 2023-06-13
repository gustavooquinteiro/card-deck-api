package com.card.deck.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalToObject;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.card.deck.domain.model.Card;
import com.card.deck.domain.model.Game;
import com.card.deck.domain.model.Hand;
import com.card.deck.domain.model.Player;
import com.card.deck.domain.repository.CardRepository;
import com.card.deck.domain.repository.GameRepository;
import com.card.deck.domain.repository.HandRepository;
import com.card.deck.domain.repository.PlayerRepository;
import com.card.deck.util.DatabaseCleaner;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class GetWinnerTest {

	private static String SUIT = "Clubs";
	private static String JACK = "JACK";
	private static String QUEEN = "QUEEN";
	private static String KING = "KING";
	private static String TEN = "10";
	private static String NINE = "9";
	private static String EIGHT = "8";
	private static String SEVEN = "7";
	private static String SIX = "6";
	private static String FIVE = "5";
	private static String FOUR = "4";
	private static String THREE = "3";
	private static String TWO = "2";
	private static String ACE = "ACE";
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner dbCleaner;
	
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private HandRepository handRepository;
	
	@Autowired
	private PlayerRepository playerRepository;
	
	private Game winningGame;
	private Game drawingGame;
	
	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/game";
		dbCleaner.clearTables();
		prepareData();
	}
	
	private Long id = 0L;
	private Long getId() {
		id += 1L;
		return id;
	}
	
	private Hand createHandFrom(List<Card> cards) {
		Hand hand = handRepository.save(new Hand());
		List<Card> finalCards = IntStream.range(0, cards.size())
				.mapToObj(i -> { 
					Card card = cards.get(i);
					card.setHand(hand);
					return cardRepository.save(card);
					})
				.toList();
		hand.setCards(finalCards);
		return hand;
	}
	
	private Game createGameFrom(List<Player> players, boolean winning) {
		if (winning) {
			winningGame = gameRepository.save(new Game());
		} else {
			drawingGame = gameRepository.save(new Game());
		}
		
		Game currentGame = (winning)? winningGame : drawingGame;
		
		List<Player> finalPlayers = players.stream()
				.map(player -> {
					player.setPlayerId(getId());
					player.setGame(currentGame);
					return playerRepository.saveAndFlush(player);
				})
				.toList();
		currentGame.setPlayers(finalPlayers);
		return gameRepository.saveAndFlush(currentGame); 
	}
	
	private void prepareData() {
			
		Player winner = new Player();
		// player hand = 55
		List<Card> winningCards = List.of(new Card(SUIT, NINE), new Card(SUIT, JACK), new Card(SUIT, QUEEN), new Card(SUIT, KING), new Card(SUIT, TEN));
		winner.setPlayerHand(createHandFrom(winningCards));
		
		Player player1 = new Player();
		// player hand = 1+2+3+4+5 = 15
		List<Card> losingCards1 = List.of(new Card(SUIT, ACE), new Card(SUIT, TWO), new Card(SUIT, THREE), new Card(SUIT, FOUR), new Card(SUIT, FIVE)); 
		player1.setPlayerHand(createHandFrom(losingCards1));

		Player player2 = new Player();
		// player hand = 8+9+2+1+11 = 31
		List<Card> losingCards2 = List.of(new Card(SUIT, EIGHT), new Card(SUIT, NINE), new Card(SUIT, TWO), new Card(SUIT, ACE), new Card(SUIT, JACK));
		player2.setPlayerHand(createHandFrom(losingCards2));
		
		Player player3 = new Player();
		// player hand = 2+2+5+7+2 = 18
		List<Card> losingCards3 = List.of(new Card(SUIT, TWO), new Card(SUIT, TWO), new Card(SUIT, FIVE), new Card(SUIT, SEVEN), new Card(SUIT, TWO));
		player3.setPlayerHand(createHandFrom(losingCards3));
		
		winningGame = createGameFrom(List.of(winner, player1, player2, player3), true);
				
		player1.setGame(drawingGame);
		player2.setGame(drawingGame);
		player3.setGame(drawingGame);
		
		Player player4 = new Player();
		// player hand = 6+9+2+1+13 = 
		List<Card> losingCards4 = List.of(new Card(SUIT, SIX), new Card(SUIT, NINE), new Card(SUIT, TWO), new Card(SUIT, ACE), new Card(SUIT, KING));
		player4.setPlayerHand(createHandFrom(losingCards4));
		
		drawingGame = createGameFrom(List.of(player1, player2, player3, player4), false);
	}

	@Test
	public void shouldReturnOneWinner_whenOnePlayersCardHaveTheBiggestSum() {
		given()
			.pathParam("gameId", winningGame.getGameId())
		.when()
			.get("{gameId}/winner")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("hasWinner", equalToObject(true))
			.body("isDraw", equalToObject(false));
	}
	
	@Test
	public void shouldReturnAllPlayers_whenOnePlayersCardDoesntHaveTheBiggestSum() {
		given()
			.pathParam("gameId", drawingGame.getGameId())
		.when()
			.get("{gameId}/winner")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("hasWinner", equalToObject(false))
			.body("isDraw", equalToObject(true));
	}
}
