package com.card.deck.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.card.deck.domain.model.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Long>{

	Optional<Game> findByDeckId(String deckId);
}
