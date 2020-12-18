package ru.monetarys.web.ro;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRo {

    private BigDecimal amount;
    private String messageToPayee;

}
