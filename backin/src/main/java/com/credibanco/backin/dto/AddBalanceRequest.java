package com.credibanco.backin.dto;

import lombok.Data;

public class AddBalanceRequest {

    String cardId;

    Double balance;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
