package ru.monetarys.integration.messages.entities;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class IbsoData {

    private String requestId;
    private String accountNumberExternalId;
    private String lastName;
    private String firstName;
    private String middleName;
    private BigDecimal amount;
    private LocalDate operationDate;
    private String currencyId;
    private String receiverBankName;
    private String purpose;

}
