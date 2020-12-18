package ru.monetarys.web.ro;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransferResponseRo {

    private PayeeAccountResponseRo payeeAccount;
    private PayerAccountResponseRo payerAccount;
    private TransactionResponseRo transaction;

}
