package com.credibanco.backin.controller;

import com.credibanco.backin.dto.AnulateTransactionRequest;
import com.credibanco.backin.dto.CreateTransactionRequest;
import com.credibanco.backin.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping(path = "/transaction/purchase")
    public ResponseEntity<Object> createTransaction(@RequestBody CreateTransactionRequest createTransactionRequest){
        return ResponseEntity.ok(transactionService.createTransaction(createTransactionRequest));
    }

    @GetMapping(path = "/transaction/{transactionId}")
    public ResponseEntity<Object> getTransaction(@PathVariable String transactionId){
        return ResponseEntity.ok(transactionService.getTransaction(transactionId));
    }

    @PostMapping(path = "/transaction/anulation")
    public ResponseEntity<Object> anulTransaction(@RequestBody AnulateTransactionRequest anulateTransactionRequest){
        return ResponseEntity.ok(transactionService.anulateTransaction(anulateTransactionRequest));
    }
}
