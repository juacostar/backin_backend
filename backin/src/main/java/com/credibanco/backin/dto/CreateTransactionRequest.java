package com.credibanco.backin.dto;

import lombok.Data;


public class CreateTransactionRequest {

    String cardId;

    Double price;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
