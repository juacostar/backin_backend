package com.credibanco.backin.dto;

import lombok.Data;


public class GetBalanceCardResponse {

    Double balance;

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
