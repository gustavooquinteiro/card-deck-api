package com.card.deck.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.card.deck.domain.model.Deck;
import com.card.deck.domain.model.Hand;

@SpringBootTest
public class ExternalCardApiTest {

	private ExternalCardApiService externalCardApiService;
	private Deck deck;
	
	@BeforeEach
	public void setUp() {
		externalCardApiService = new ExternalCardApiService();
		deck = externalCardApiService.retrieveDeck();
	}
	
	@Test
	public void shouldRetrieveNewDeck() {
		assertNotNull(deck);
	}
	
	@Test
	public void shouldGetDefaultAmountOfCardsFromAGivenDeck() {
		String deckId = deck.getDeck_id();
		deck.setCards(externalCardApiService.getCardsFromDeck(deckId));
		int handSize = deck.getCards().size();
		assertEquals(Hand.HAND_DEFAULT_SIZE, handSize);
	}
}
