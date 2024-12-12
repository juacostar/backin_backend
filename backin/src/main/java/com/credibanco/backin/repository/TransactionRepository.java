package com.credibanco.backin.repository;

import com.credibanco.backin.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
    Transaction findTransactionByTransactionId(String transactionId);
}
