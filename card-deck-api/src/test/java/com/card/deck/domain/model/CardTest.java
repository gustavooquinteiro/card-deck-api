package com.card.deck.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CardTest {

	@Test
	void shouldReturnCorrectCardValue_whenNonNumericalRankCardValue() {
		Card aceCard = new Card("Hearts", "A");
		Card jesterCard = new Card("Hearts", "J");
		Card queenCard = new Card("Hearts", "Q");
		Card kingCard = new Card("Hearts", "K");
		
		assertEquals(Card.ACE_VALUE, aceCard.getValue());
		assertEquals(Card.JESTER_VALUE, jesterCard.getValue());
		assertEquals(Card.QUEEN_VALUE, queenCard.getValue());
		assertEquals(Card.KING_VALUE, kingCard.getValue());
	}
	
	@Test
	void shouldReturnCorrectCardValue_whenNumericalRankCardValue() {
		for (int cardValue = 1; cardValue <= 10; cardValue++) {
			Card card = new Card("Hearts", String.valueOf(cardValue));
			assertEquals(cardValue, card.getValue());
		}
	}
}
