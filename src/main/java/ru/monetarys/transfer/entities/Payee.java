package ru.monetarys.transfer.entities;

import lombok.Data;

@Data
public class Payee {

    private String guid;
    private String accountId;
    private String account;
    private String cardNumber;
    private String phone;

}
