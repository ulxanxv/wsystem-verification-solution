package ru.monetarys.transfer.entities;

import lombok.Builder;
import lombok.Data;
import ru.monetarys.models.TransferStatus;

import java.math.BigDecimal;

@Data
@Builder
public class TransactionResponse {

    private String id;
    private TransferStatus transferStatus;
    private BigDecimal amount;
    private String currency;

}
