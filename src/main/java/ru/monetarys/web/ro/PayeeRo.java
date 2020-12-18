package ru.monetarys.web.ro;

import lombok.Data;

@Data
public class PayeeRo {

    private String guid;
    private String accountId;
    private String account;
    private String cardNumber;
    private String phone;

}
