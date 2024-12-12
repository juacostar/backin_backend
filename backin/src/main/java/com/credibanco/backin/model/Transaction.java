package com.credibanco.backin.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "price")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @Column(name = "date_transaction")
    private LocalDateTime dateTransaction;

    @Column(name = "anulled")
    private Boolean anulled;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public LocalDateTime getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(LocalDateTime dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public Boolean getAnulled() {
        return anulled;
    }

    public void setAnulled(Boolean anulled) {
        this.anulled = anulled;
    }
}
