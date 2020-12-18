package ru.monetarys.web.domain;

import lombok.Data;

@Data
public class TransferRequest {

    private Payer payer;
    private Payee payee;
    private Transaction transaction;

}
