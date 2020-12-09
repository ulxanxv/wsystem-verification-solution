package ru.monetarys.messages.entities;

import lombok.Data;
import ru.monetarys.models.TransferStatus;

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
