package com.credibanco.backin.dto;

import lombok.Data;


public class CreateCardError {

    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
