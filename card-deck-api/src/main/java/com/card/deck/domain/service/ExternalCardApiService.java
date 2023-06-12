package com.card.deck.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.card.deck.api.dto.DeckResponseExternalApiDTO;
import com.card.deck.domain.model.Card;
import com.card.deck.domain.model.Deck;

@Service
public class ExternalCardApiService {

	private static String DECK_OF_CARDS_API_URL = "https://deckofcardsapi.com/api/deck/";
	private static String NEW_SHUFFLE_URL = "new/shuffle/";
	private static String DRAW_URL = "/draw/";
	private static String CARD_QUANTITY_PARAM = "?count=";

	private final RestTemplate deckOfCardsApiTemplate = new RestTemplate();

	public Deck retrieveDeck() {
		return deckOfCardsApiTemplate.getForObject(DECK_OF_CARDS_API_URL + NEW_SHUFFLE_URL, Deck.class);
	}

	public List<Card> getCardsFromDeck(String deckId, int cardsQuantity) {
		String uri = DECK_OF_CARDS_API_URL + deckId + DRAW_URL + CARD_QUANTITY_PARAM + cardsQuantity;
		DeckResponseExternalApiDTO cardResponse = deckOfCardsApiTemplate.getForObject(uri, DeckResponseExternalApiDTO.class);
		return cardResponse.cards().stream()
				.map(card -> new Card(card.suit(), card.value()))
				.collect(Collectors.toList());
	}

}
