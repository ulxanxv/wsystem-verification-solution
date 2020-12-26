package ru.monetarys.ro.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.monetarys.dao.models.TransferStatus;

import java.math.BigDecimal;

@Data
@Builder
public class TransferResponseRo {

    private PayeeRo payeeAccount;
    private PayerRo payerAccount;
    private TransactionRo transaction;

    @Data
    @AllArgsConstructor
    public static class PayerRo {
        private String account;
    }

    @Data
    @AllArgsConstructor
    public static class PayeeRo {
        private String account;
    }

    @Data
    @Builder
    public static class TransactionRo {
        private String id;
        private TransferStatus status;
        private BigDecimal amount;
        private String currency;
    }

}
