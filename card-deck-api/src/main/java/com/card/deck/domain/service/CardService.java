package com.card.deck.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.card.deck.domain.model.Card;
import com.card.deck.domain.repository.CardRepository;

@Service
public class CardService {

	@Autowired
	private CardRepository cardRepository;
	
	public Card saveCard(Card card) {
		card.setCardId((long) card.hashCode());
		return this.cardRepository.save(card);
	}
	
	public List<Card> saveCards(List<Card> cards) {
		cards.forEach(card -> card.setCardId((long) card.hashCode()));
		return cardRepository.saveAll(cards);
	}
}
