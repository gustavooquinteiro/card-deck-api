package com.card.deck.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CardTest {

	private static String ACE_NAME = "ACE";
	private static String JACK_NAME = "JACK";
	private static String QUEEN_NAME = "QUEEN";
	private static String KING_NAME = "KING";
	private static String SUIT = "Hearts";
	
	@Test
	void shouldReturnCorrectCardValue_whenNonNumericalRankCardValue() {
		Card aceCard = new Card(SUIT, ACE_NAME);
		Card jesterCard = new Card(SUIT, JACK_NAME);
		Card queenCard = new Card(SUIT, QUEEN_NAME);
		Card kingCard = new Card(SUIT, KING_NAME);
		
		assertEquals(Card.ACE_VALUE, aceCard.getIntValue());
		assertEquals(Card.JACK_VALUE, jesterCard.getIntValue());
		assertEquals(Card.QUEEN_VALUE, queenCard.getIntValue());
		assertEquals(Card.KING_VALUE, kingCard.getIntValue());
	}
	
	@Test
	void shouldReturnCorrectCardValue_whenNumericalRankCardValue() {
		for (int cardValue = 2; cardValue <= 10; cardValue++) {
			Card card = new Card(SUIT, String.valueOf(cardValue));
			assertEquals(cardValue, card.getIntValue());
		}
	}
}
