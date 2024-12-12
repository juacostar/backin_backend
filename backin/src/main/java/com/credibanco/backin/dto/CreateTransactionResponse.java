package com.credibanco.backin.dto;

import lombok.Data;


public class CreateTransactionResponse {

    String transactionId;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
