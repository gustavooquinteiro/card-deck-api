package com.card.deck.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.card.deck.domain.model.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Long>{

	Game findByDeckId(String deckId);
}
