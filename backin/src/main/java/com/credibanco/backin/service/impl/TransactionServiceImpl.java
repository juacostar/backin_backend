package com.credibanco.backin.service.impl;

import com.credibanco.backin.dto.AnulateTransactionRequest;
import com.credibanco.backin.dto.CreateTransactionRequest;
import com.credibanco.backin.dto.CreateTransactionResponse;
import com.credibanco.backin.dto.MessageResponse;
import com.credibanco.backin.dto.GetTransactionResponse;
import com.credibanco.backin.repository.CardRepository;
import com.credibanco.backin.model.Card;
import com.credibanco.backin.model.Transaction;
import com.credibanco.backin.repository.TransactionRepository;
import com.credibanco.backin.service.TransactionService;
import com.credibanco.backin.util.TransactionMapper;
import com.credibanco.backin.util.ValidateTransaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private ValidateTransaction validateTransaction;

    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
    private final ObjectMapper mapper = new ObjectMapper();
    @Override
    public Object createTransaction(CreateTransactionRequest createTransactionRequest) {
        try{
            logger.info("---------- Begin Process createTransaction ----------");
            logger.info("Request: {}", mapper.writeValueAsString(createTransactionRequest));

            Card card = cardRepository.findCardByCardid(createTransactionRequest.getCardId());
            if(card.getBalance() < createTransactionRequest.getPrice()){
                throw new Exception("not enough balance");
            }
            Transaction transaction = transactionMapper.requestToEntity(createTransactionRequest, card);
            Transaction saved = transactionRepository.save(transaction);
            CreateTransactionResponse createTransactionResponse = new CreateTransactionResponse();
            createTransactionResponse.setTransactionId(saved.getTransactionId());
            card.setBalance(card.getBalance() - transaction.getPrice());
            cardRepository.save(card);

            logger.info("Response: {}", mapper.writeValueAsString(createTransactionResponse));
            logger.info("---------- End Process createTransaction ----------");
            return createTransactionResponse;
        }catch (Exception e){

            MessageResponse messageResponse = new MessageResponse();

            messageResponse.setMessage(e.getMessage());
            logger.info("Error: {}. Detail: {}", e.getMessage(), e.getStackTrace());
            logger.info("---------- End Process createTransaction ----------");
            return  messageResponse;

        }
    }

    @Override
    public Object getTransaction(String transactionId) {
        try {

            logger.info("---------- Begin Process getTransaction ----------");
            logger.info("Request: {}", transactionId);
            Transaction transaction = transactionRepository.findTransactionByTransactionId(transactionId);

            GetTransactionResponse getTransactionResponse = new GetTransactionResponse();
            getTransactionResponse.setCardId(transaction.getCard().getCardid());
            getTransactionResponse.setPrice(transaction.getPrice());
            getTransactionResponse.setTransactionId(transaction.getTransactionId());

            logger.info("Response: {}", mapper.writeValueAsString(getTransactionResponse));
            logger.info("---------- End Process getTransaction ----------");
            return  getTransactionResponse;

        }catch (Exception e){

            MessageResponse messageResponse = new MessageResponse();
            messageResponse.setMessage(e.getMessage());
            logger.info("Error: {}. Detail: {}", e.getMessage(), e.getStackTrace());
            logger.info("---------- End Process getTransaction ----------");
            return messageResponse;

        }
    }

    @Override
    public Object anulateTransaction(AnulateTransactionRequest anulateTransactionRequest) {
        MessageResponse messageResponse = new MessageResponse();
        try{
            logger.info("---------- Begin Process anulateTransaction ----------");
            logger.info("Request: {}", mapper.writeValueAsString(anulateTransactionRequest));

            Card card = cardRepository.findCardByCardid(anulateTransactionRequest.getCardId());
            Transaction transaction = transactionRepository.findTransactionByTransactionId(anulateTransactionRequest.getTransactionId());
            if(validateTransaction.validateNullTransaction(transaction)){
                transaction.setAnulled(true);
                transactionRepository.save(transaction);
            }
            else{

                messageResponse.setMessage("Transaction exceed 24 hours");
                return messageResponse;
            }

            card.setBalance(card.getBalance() + transaction.getPrice());
            cardRepository.save(card);
            messageResponse.setMessage("anulled");

            logger.info("Response: {}", mapper.writeValueAsString(messageResponse));
            logger.info("---------- End Process anulateTransaction ----------");
            return  messageResponse;


        }catch(Exception e){
            messageResponse.setMessage(e.getMessage());
            logger.info("Error: {}. Detail: {}", e.getMessage(), e.getStackTrace());
            logger.info("---------- End Process anulateTransaction ----------");
            return messageResponse;

        }
    }
}
