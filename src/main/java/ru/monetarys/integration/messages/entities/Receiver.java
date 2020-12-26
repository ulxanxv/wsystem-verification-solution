package ru.monetarys.integration.messages.entities;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Receiver {

    private String messageId;
    private LocalDateTime messageDatetime;
    private String originator;
    private String transactionId;
    private String transactionType;
    private String transactionStatus;
    private LocalDateTime transactionDatetime;
    private String accountNumber;

}
