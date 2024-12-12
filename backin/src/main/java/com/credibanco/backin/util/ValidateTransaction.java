package com.credibanco.backin.util;

import com.credibanco.backin.model.Transaction;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class ValidateTransaction {
    public Boolean validateNullTransaction(Transaction transaction){
        if(validateTransactionDate(transaction.getDateTransaction())) return  true;
        else return false;
    }

    public Boolean validateTransactionDate(LocalDateTime date){

        LocalDateTime localDate = LocalDateTime.now();
        if(localDate.until(date, ChronoUnit.HOURS) > 24) return false;
        else return true;

    }

}
