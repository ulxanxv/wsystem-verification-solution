package ru.monetarys.transfer.entities;

import lombok.Data;

@Data
public class TransferRequest {

    private Payer payer;
    private Payee payee;
    private Transaction transaction;

}
