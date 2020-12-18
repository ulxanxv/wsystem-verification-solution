package ru.monetarys.web.domain;

import lombok.Data;

@Data
public class Payee {

    private String guid;
    private String accountId;
    private String account;
    private String cardNumber;
    private String phone;

}
