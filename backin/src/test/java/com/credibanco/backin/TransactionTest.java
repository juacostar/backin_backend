package com.credibanco.backin;

import com.credibanco.backin.dto.CreateCardRequest;
import com.credibanco.backin.dto.CreateTransactionRequest;
import com.credibanco.backin.model.Card;
import com.credibanco.backin.model.Transaction;
import com.credibanco.backin.repository.CardRepository;
import com.credibanco.backin.repository.TransactionRepository;
import com.credibanco.backin.util.CardMapper;
import com.credibanco.backin.util.TransactionMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = BackinApplication.class)
@AutoConfigureMockMvc
@Transactional
public class TransactionTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardMapper cardMapper;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    private final Jackson2ObjectMapperBuilder mapperBuilder = new Jackson2ObjectMapperBuilder();
    private final ObjectMapper mapper = mapperBuilder.build();


    CreateCardRequest createRequest(){
        CreateCardRequest createCardRequest = new CreateCardRequest();
        createCardRequest.setProductId("123456");
        createCardRequest.setName("Juan");
        createCardRequest.setLastName("Acosta");

        return createCardRequest;
    }
    Card createCardObject(){
        return cardMapper.requestToEntity(createRequest());
    }

    CreateTransactionRequest createTransaction(String cardId){
        CreateTransactionRequest createTransactionRequest = new CreateTransactionRequest();
        createTransactionRequest.setCardId(cardId);
        createTransactionRequest.setPrice(10.0);

        return  createTransactionRequest;
    }

    Transaction createTransactionObject(String cardId, Card card){
        return  transactionMapper.requestToEntity(createTransaction(cardId), card);
    }
    @Test
    void purchaseTransaction() throws  Exception{
        Card card = createCardObject();
        card.setBalance(1000.0);

        cardRepository.save(card);

        CreateTransactionRequest createTransactionRequest = createTransaction(card.getCardid());
        this.mockMvc.perform(post("/transaction/purchase")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createTransactionRequest))
        ).andExpect(status().isOk());

        cardRepository.delete(card);
    }

    @Test
    void getTransaction() throws Exception{

        Card card = createCardObject();
        card.setBalance(1000.0);

        cardRepository.save(card);

        Transaction transaction = createTransactionObject(card.getCardid(), card);
        transactionRepository.save(transaction);
        CreateTransactionRequest createTransactionRequest = createTransaction(card.getCardid());
        this.mockMvc.perform(get("/transaction/" + transaction.getTransactionId())
        ).andExpect(status().isOk());

        transactionRepository.delete(transaction);

        cardRepository.delete(card);

    }



}
