package ru.monetarys.integration.messages.entities;

import lombok.Data;
import ru.monetarys.dao.models.TransferStatus;

import java.time.LocalDateTime;

@Data
public class Sender {

    private String messageId;
    private LocalDateTime messageDatetime;
    private String originator;
    private String transactionId;
    private String transactionType;
    private TransferStatus transactionStatus;
    private String accountNumber;

}
