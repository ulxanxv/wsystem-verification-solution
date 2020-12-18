package ru.monetarys.web.domain;

import lombok.Data;
import ru.monetarys.models.TransferStatus;

import java.math.BigDecimal;

@Data
public class Transaction {

    private String id;
    private TransferStatus transferStatus;
    private BigDecimal amount;
    private String currency;
    private String messageToPayee;

}
