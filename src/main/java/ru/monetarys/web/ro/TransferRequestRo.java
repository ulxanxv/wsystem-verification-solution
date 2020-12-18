package ru.monetarys.web.ro;

import lombok.Data;

@Data
public class TransferRequestRo {

    private PayerRo payer;
    private PayeeRo payee;
    private TransactionRo transaction;

}
