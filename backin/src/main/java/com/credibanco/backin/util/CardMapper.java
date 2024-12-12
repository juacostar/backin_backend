package com.credibanco.backin.util;

import com.credibanco.backin.dto.CreateCardRequest;
import com.credibanco.backin.model.Card;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

@Component
public class CardMapper {

    public Card requestToEntity(CreateCardRequest request){
        Card card = new Card();
        card.setCardid(generateCardId(request.getProductId()));
        card.setActivated(false);
        card.setBalance(0.0);
        card.setTransactions(new ArrayList<>());
        card.setExpiredDate(generateExpiredate());
        card.setName(request.getName() + request.getLastName());

        return card;
    }

    public String generateCardId(String productId){

        Random random = new Random();
        long min = 1000000000L;
        long max = 9999999999L;

        return productId + Long.toString(min + ((long) (random.nextDouble()*(max - min)) ) );
    }

    public LocalDate generateExpiredate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");

        LocalDate date = LocalDate.now();

        return date.plusYears(3);
    }
}
