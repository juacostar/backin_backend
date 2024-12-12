package com.credibanco.backin.util;

import com.credibanco.backin.dto.CreateTransactionRequest;
import com.credibanco.backin.model.Card;
import com.credibanco.backin.model.Transaction;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Component
public class TransactionMapper {
    public Transaction requestToEntity(CreateTransactionRequest createTransactionRequest, Card card){

        Transaction transaction = new Transaction();
        transaction.setTransactionId(generateTransactionId());
        transaction.setPrice(createTransactionRequest.getPrice());
        transaction.setCard(card);
        transaction.setDateTransaction(LocalDateTime.now());
        transaction.setAnulled(false);

        return  transaction;
    }

    public String generateTransactionId(){

        Random random = new Random();
        long min = 100000L;
        long max = 999999L;

        return Long.toString(min + ((long) (random.nextDouble()*(max - min)) ) );
    }

}

