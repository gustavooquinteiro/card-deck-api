package com.card.deck.api;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.card.deck.util.DatabaseCleaner;
import com.card.deck.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CreateCustomGameTest {

	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner dbCleaner;
	
	private static String newCustomGameBasePath = "/game/custom/new";
	
	private String jsonCorrectCustomGameConfig;
	private String jsonIncorrectCustomGameConfigWithDifferentPlayersAmount;
	private String jsonIncorrectCustomGameConfigWithDifferentCardsAmount;

	@BeforeEach
	public void setUp(){
		enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.basePath = newCustomGameBasePath;
		RestAssured.port = port;
		jsonCorrectCustomGameConfig = ResourceUtils.getContentFromResource("/json/correct/custom_game_configuration.json");
		jsonIncorrectCustomGameConfigWithDifferentPlayersAmount = ResourceUtils.getContentFromResource("/json/incorrect/custom_game_configuration_with_different_player_amount.json");
		jsonIncorrectCustomGameConfigWithDifferentCardsAmount = ResourceUtils.getContentFromResource("/json/incorrect/custom_game_configuration_with_different_card_amount.json");
		dbCleaner.clearTables();
	}
	
	@Test
	public void shouldCreateNewCustomGame_whenCorrectConfigurationIsSent() {
		given()
			.accept(ContentType.JSON)
			.body(jsonCorrectCustomGameConfig)
			.contentType(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	
	@Test
	public void shouldNotCreateNewCustomGame_whenIncorrectAmountOfPlayersIsSent() {
		given()
			.accept(ContentType.JSON)
			.body(jsonIncorrectCustomGameConfigWithDifferentPlayersAmount)
			.contentType(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void shouldNotCreateNewCustomGame_whenDifferentAmountOfCardsAmongPlayersIsSent() {
		given()
			.accept(ContentType.JSON)
			.body(jsonIncorrectCustomGameConfigWithDifferentCardsAmount)
			.contentType(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
}
