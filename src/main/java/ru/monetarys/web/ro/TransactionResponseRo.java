package ru.monetarys.web.ro;

import lombok.Builder;
import lombok.Data;
import ru.monetarys.models.TransferStatus;

import java.math.BigDecimal;

@Data
@Builder
public class TransactionResponseRo {

    private String id;
    private TransferStatus status;
    private BigDecimal amount;
    private String currency;

}
