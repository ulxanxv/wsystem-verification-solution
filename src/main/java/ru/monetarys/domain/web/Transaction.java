package ru.monetarys.domain.web;

import lombok.Data;
import ru.monetarys.dao.models.TransferStatus;

import java.math.BigDecimal;

@Data
public class Transaction {

    private String id;
    private TransferStatus transferStatus;
    private BigDecimal amount;
    private String currency;
    private String messageToPayee;

}
