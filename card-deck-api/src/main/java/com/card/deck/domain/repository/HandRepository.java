package com.card.deck.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.card.deck.domain.model.Hand;

@Repository
public interface HandRepository extends JpaRepository<Hand, Long> {

}
