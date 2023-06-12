package com.card.deck.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.card.deck.domain.model.Card;
import com.card.deck.domain.model.Hand;
import com.card.deck.domain.repository.HandRepository;

@Service
public class HandService {

	@Autowired
	private HandRepository handRepository;
	
	public Hand saveHand(Hand hand) {
		return handRepository.save(hand);
	}
	
	public Hand saveHand(List<Card> cards) {
		Hand hand = new Hand();
		hand.setCards(cards);
		hand.setHandId((long) hand.hashCode());
		return saveHand(hand);
	}
}
