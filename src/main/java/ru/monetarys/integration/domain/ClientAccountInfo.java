package ru.monetarys.integration.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClientAccountInfo {

    private String accountStatus;
    private String accountType;
    private String accountId;
    private String currency;
    private String bankName;
    private BigDecimal balance;
    private String bic;

}
