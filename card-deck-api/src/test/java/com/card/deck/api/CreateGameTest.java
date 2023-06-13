package com.card.deck.api;
import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.card.deck.api.dto.GameRequestDTO;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CreateGameTest {

	@LocalServerPort
	private int port;
	
	private static String newGameBasePath = "/game/new";
	private static int correctPlayerQuantity = 2;
	private static int incorrectPlayerQuantity = 1;
	private static int correctCardQuantity = 1;
	private static int incorrectCardQuantity = 0;
	
	@BeforeEach
	public void setUp() {
		enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.basePath = newGameBasePath;
		RestAssured.port = port;
	}
	
	@Test
	public void shouldCreateNewGame_whenDefaultConfigurationisUsed() {
		given()
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void shouldCreateNewGame_whenCorrectGameRequestIsSent() {
		GameRequestDTO gameConfiguration = new GameRequestDTO(correctPlayerQuantity, correctCardQuantity, "");
		given()
			.contentType(ContentType.JSON)
			.body(gameConfiguration)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void shouldNotCreateNewGame_whenIncorrectPlayersQuantityInGameRequestIsSent() {
		GameRequestDTO gameConfiguration = new GameRequestDTO(incorrectPlayerQuantity, correctCardQuantity, "");
		given()
			.contentType(ContentType.JSON)
			.body(gameConfiguration)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void shouldNotCreateNewGame_whenIncorrectCardQuantityInGameRequestIsSent() {
		GameRequestDTO gameConfiguration = new GameRequestDTO(correctPlayerQuantity, incorrectCardQuantity, "");
		given()
			.contentType(ContentType.JSON)
			.body(gameConfiguration)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void shouldNotCreateNewGame_whenIncorrectDeckIdInGameRequestIsSent() {
		GameRequestDTO gameConfiguration = new GameRequestDTO(correctPlayerQuantity, correctCardQuantity, "asasasa");
		given()
			.contentType(ContentType.JSON)
			.body(gameConfiguration)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
}
