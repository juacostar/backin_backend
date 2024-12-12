package com.credibanco.backin.repository;

import com.credibanco.backin.model.Card;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends CrudRepository<Card, Integer> {

    Card findCardByCardid(String cardId);
}
