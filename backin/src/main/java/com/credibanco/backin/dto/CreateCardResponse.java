package com.credibanco.backin.dto;

import lombok.Data;


public class CreateCardResponse {

    private String cardId;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
