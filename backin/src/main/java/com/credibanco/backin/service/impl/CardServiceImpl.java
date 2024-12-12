package com.credibanco.backin.service.impl;

import com.credibanco.backin.dto.*;
import com.credibanco.backin.model.Card;
import com.credibanco.backin.repository.CardRepository;
import com.credibanco.backin.service.CardService;
import com.credibanco.backin.util.CardMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardMapper cardMapper;

    private final ObjectMapper mapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(CardServiceImpl.class);
    @Override
    public Object createCard(CreateCardRequest request) {

        try{
            logger.info("---------- Begin process createCard ---------- ");
            logger.info("Request: {}", mapper.writeValueAsString(request));
            Card card = cardMapper.requestToEntity(request);
            Card created = cardRepository.save(card);
            CreateCardResponse createCardResponse = new CreateCardResponse();
            createCardResponse.setCardId(created.getCardid());
            logger.info("Response: {}", mapper.writeValueAsString(createCardResponse));
            logger.info("---------- End process createCard ---------- ");
            return  createCardResponse;
        }catch(Exception e){
            CreateCardError createCardError = new CreateCardError();
            createCardError.setMessage(e.getMessage());
            logger.info("Error: {} detail: {}", e.getMessage(), e.getStackTrace());
            logger.info("---------- End process createCard ---------- ");
            return createCardError;
        }
    }

    @Override
    public Object activateCard(ActivateCardRequest activateCardRequest) {

        MessageResponse messageResponse = new MessageResponse();

        try {
            logger.info("---------- Begin process activateCard ---------- ");
            logger.info("Request: {}", mapper.writeValueAsString(activateCardRequest));
            Card card = cardRepository.findCardByCardid(activateCardRequest.getCardId());
            card.setActivated(true);

            cardRepository.save(card);
            messageResponse.setMessage("activated");
            logger.info("Response: {}", mapper.writeValueAsString(messageResponse));
            logger.info("---------- End process activateCard ---------- ");
            return messageResponse;

        }catch (Exception e){
            messageResponse.setMessage(e.getMessage());
            logger.info("Error: {} detail: {}", e.getMessage(), e.getStackTrace());
            logger.info("---------- End process activateCard ---------- ");
            return messageResponse;
        }

    }

    @Override
    public Object blockCard(String cardId) {
        MessageResponse messageResponse = new MessageResponse();

        try{
            logger.info("---------- Begin process blockCard ---------- ");
            logger.info("Request: {}", cardId);
            Card card = cardRepository.findCardByCardid(cardId);
            cardRepository.delete(card);
            messageResponse.setMessage("deleted");
            logger.info("Response: {}", mapper.writeValueAsString(messageResponse));
            logger.info("---------- End process blockCard ---------- ");
            return messageResponse;

        }catch (Exception e){
            messageResponse.setMessage(e.getMessage());
            logger.info("Error: {} detail: {}", e.getMessage(), e.getStackTrace());
            logger.info("---------- End process blockCard ---------- ");
            return messageResponse;
        }
    }

    @Override
    public Object addBalance(AddBalanceRequest addBalanceRequest) {
        MessageResponse messageResponse = new MessageResponse();

        try{
            logger.info("---------- Begin process addBalance ---------- ");
            logger.info("Request: {}", mapper.writeValueAsString(addBalanceRequest));
            Card card = cardRepository.findCardByCardid(addBalanceRequest.getCardId());
            if(card.getActivated()) {
                card.setBalance(card.getBalance() + addBalanceRequest.getBalance());
                cardRepository.save(card);
                messageResponse.setMessage("updated");
            }else{
                messageResponse.setMessage("card inactivated");
            }

            logger.info("Response: {}", mapper.writeValueAsString(messageResponse));
            logger.info("---------- End process addBalance ---------- ");
            return  messageResponse;

        }catch(Exception e){
            messageResponse.setMessage(e.getMessage());
            logger.info("Error: {} detail: {}", e.getMessage(), e.getStackTrace());
            logger.info("---------- End process addBalance ---------- ");
            return messageResponse;
        }
    }

    @Override
    public Object getBalance(String cardId) {
        try{
            logger.info("---------- Begin process getBalance ---------- ");
            logger.info("Request: {}",cardId);
            GetBalanceCardResponse getBalanceCardResponse = new GetBalanceCardResponse();
            Card card = cardRepository.findCardByCardid(cardId);
            getBalanceCardResponse.setBalance(card.getBalance());

            logger.info("Response: {}", mapper.writeValueAsString(getBalanceCardResponse));
            logger.info("---------- End process getBalance ---------- ");
            return  getBalanceCardResponse;

        }catch (Exception e){
            MessageResponse messageResponse = new MessageResponse();
            messageResponse.setMessage(e.getMessage());
            logger.info("Error: {} detail: {}", e.getMessage(), e.getStackTrace());
            logger.info("---------- End process getBalance ---------- ");
            return messageResponse;
        }
    }
}
