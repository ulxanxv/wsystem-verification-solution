package ru.monetarys.integration.messages.entities;

import lombok.Data;

@Data
public class TransferFeedback {

    private Header header;
    private Sender sender;
    private Receiver receiver;
    private IbsoData ibsoData;

}
