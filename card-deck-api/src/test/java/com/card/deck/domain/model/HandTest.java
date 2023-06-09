package com.card.deck.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HandTest {

	private static String SUIT = "Hearts"; 
	
	private Hand hand;
	
	@BeforeEach
	void setUp() {
		hand = new Hand();
		Card nineCard = new Card(SUIT, "9");
		Card jesterCard = new Card(SUIT, "J");
		Card queenCard = new Card(SUIT, "Q");
		Card kingCard = new Card(SUIT, "K");
		Card tenCard = new Card(SUIT, "10");
		List<Card> cards = new ArrayList<>();
		cards.add(tenCard);
		cards.add(queenCard);
		cards.add(jesterCard);
		cards.add(kingCard);
		cards.add(nineCard);
		hand.setCards(cards);
	}
	
	@Test
	void shouldSumHandValueCorrectly() {
		assertEquals(55, hand.getHandValue());
	}
	
	@Test
	void shouldNotAddNewCard() {
		Card card = new Card(SUIT, "4");
		hand.addCard(card);
		assertEquals(5, hand.getCards().size());
		
	}
}
