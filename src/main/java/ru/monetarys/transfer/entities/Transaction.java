package ru.monetarys.transfer.entities;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Transaction {

    private BigDecimal amount;
    private String messageToPayee;

}
