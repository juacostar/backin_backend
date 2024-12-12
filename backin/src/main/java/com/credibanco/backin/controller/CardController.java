package com.credibanco.backin.controller;

import com.credibanco.backin.dto.ActivateCardRequest;
import com.credibanco.backin.dto.AddBalanceRequest;
import com.credibanco.backin.dto.CreateCardRequest;
import com.credibanco.backin.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CardController {
    @Autowired
    private CardService cardService;


    @PostMapping(path = "/card/number")
    public ResponseEntity<Object> createCard(@RequestBody CreateCardRequest createCardRequest){
        return ResponseEntity.ok(cardService.createCard(createCardRequest));
    }

    @PostMapping(path = "/card/enroll")
    public ResponseEntity<Object> enrollCard(@RequestBody ActivateCardRequest activateCardRequest){
        return ResponseEntity.ok(cardService.activateCard(activateCardRequest));
    }

    @DeleteMapping(path = "/card/{cardId}")
    public ResponseEntity<Object> deleteCard(@PathVariable String cardId){
        return ResponseEntity.ok(cardService.blockCard(cardId));
    }

    @PostMapping(path = "/card/balance")
    public ResponseEntity<Object> addBalance(@RequestBody AddBalanceRequest addBalanceRequest){
        return ResponseEntity.ok(cardService.addBalance(addBalanceRequest));
    }

    @GetMapping(path = "card/balance/{cardId}")
    public ResponseEntity<Object> getBalance(@PathVariable String cardId){
        return ResponseEntity.ok(cardService.getBalance(cardId));
    }
}
