package ru.monetarys.integration.messages.entities;

import lombok.Data;
import ru.monetarys.dao.models.TransferStatus;

import java.time.LocalDateTime;

@Data
public class Header {

    private String messageId;
    private LocalDateTime messageDatetime;
    private String originator;
    private String transactionId;
    private TransferStatus transactionStatus;
    private LocalDateTime transactionDatetime;
    private String messageToPayee;

}
