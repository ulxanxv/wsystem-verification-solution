package ru.monetarys.ro.web;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequestRo {

    private PayerRo payer;
    private PayeeRo payee;
    private TransactionRo transaction;

    @Data
    public static class PayerRo {
        private String guid;
        private String account;
    }

    @Data
    public static class PayeeRo {
        private String guid;
        private String accountId;
        private String account;
        private String cardNumber;
        private String phone;
    }

    @Data
    public static class TransactionRo {
        private BigDecimal amount;
        private String messageToPayee;
    }

}
