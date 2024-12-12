package com.credibanco.backin.service;

import com.credibanco.backin.dto.ActivateCardRequest;
import com.credibanco.backin.dto.AddBalanceRequest;
import com.credibanco.backin.dto.CreateCardRequest;

public interface CardService {

    Object createCard(CreateCardRequest request);

    Object activateCard(ActivateCardRequest activateCardRequest);

    Object blockCard(String cardId);

    Object addBalance(AddBalanceRequest addBalanceRequest);

    Object getBalance(String cardId);
}
