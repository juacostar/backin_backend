package com.credibanco.backin;

import com.credibanco.backin.dto.ActivateCardRequest;
import com.credibanco.backin.dto.AddBalanceRequest;
import com.credibanco.backin.dto.CreateCardRequest;
import com.credibanco.backin.model.Card;
import com.credibanco.backin.repository.CardRepository;
import com.credibanco.backin.util.CardMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = BackinApplication.class)
@AutoConfigureMockMvc
@Transactional
public class CardTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardMapper cardMapper;

    private final Jackson2ObjectMapperBuilder mapperBuilder = new Jackson2ObjectMapperBuilder();
    private final ObjectMapper mapper = mapperBuilder.build();

    CreateCardRequest createRequest(){
        CreateCardRequest createCardRequest = new CreateCardRequest();
        createCardRequest.setProductId("123456");
        createCardRequest.setName("Juan");
        createCardRequest.setLastName("Acosta");

        return createCardRequest;
    }

    AddBalanceRequest createAddBalanceRequets(String cardId){
        AddBalanceRequest addBalanceRequest = new AddBalanceRequest();
        addBalanceRequest.setCardId(cardId);
        addBalanceRequest.setBalance(10.0);

        return addBalanceRequest;
    }

    Card createCardObject(){
        return cardMapper.requestToEntity(createRequest());
    }

    ActivateCardRequest createEnrollequest(String cardId){
        ActivateCardRequest activateCardRequest = new ActivateCardRequest();
        activateCardRequest.setCardId(cardId);

        return activateCardRequest;
    }

    @Test
    void createCard() throws Exception{
        CreateCardRequest createCardRequest = createRequest();
        this.mockMvc.perform(post("/card/number")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createCardRequest))
        ).andExpect(status().isOk());

    }

    @Test
    void enrollCard() throws Exception{

        Card card = createCardObject();

        ActivateCardRequest activateCardRequest = createEnrollequest(card.getCardid());
        this.mockMvc.perform(post("/card/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(activateCardRequest))
        ).andExpect(status().isOk());

        cardRepository.delete(card);
    }

    @Test
    void deleteCard() throws Exception{
        Card card = createCardObject();
        cardRepository.save(card);

        this.mockMvc.perform(delete("/card/"+ card.getCardid())).andExpect(status().isOk());
    }

    @Test
    void balanceCard() throws Exception{

        Card card = createCardObject();
        card.setActivated(true);
        card.setBalance(100.0);

        cardRepository.save(card);

        AddBalanceRequest addBalanceRequest = createAddBalanceRequets(card.getCardid());
        this.mockMvc.perform(post("/card/balance")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(addBalanceRequest))
        ).andExpect(status().isOk());

        cardRepository.delete(card);
    }

    @Test
    void getBalance() throws Exception{

        Card card = createCardObject();
        card.setActivated(true);
        card.setBalance(100.0);

        cardRepository.save(card);

        AddBalanceRequest addBalanceRequest = createAddBalanceRequets(card.getCardid());
        this.mockMvc.perform(get("/card/balance/" + card.getCardid())).andExpect(status().isOk());

        cardRepository.delete(card);

    }


}
