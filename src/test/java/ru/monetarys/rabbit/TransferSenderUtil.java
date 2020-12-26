package ru.monetarys.rabbit;

import lombok.experimental.UtilityClass;
import ru.monetarys.integration.messages.entities.*;

import java.time.LocalDateTime;

@UtilityClass
public class TransferSenderUtil {

    public String MESSAGE_ID = "134f861-9b92-4028-82c0-56vfah098a52";
    public String TRANSACTION_ID = "1AB23456C7890123D";
    public String ORIGINATOR = "Ivan";
    public String MESSAGE_TO_PAYEE = "You got $5000";
    public String FIRST_NAME = "Ivanov";
    public String LAST_NAME = "Ivan";
    public String ACCOUNT_NUMBER = "1FA-AA";

    public String IN_EXCHANGE = "bank_transfers.in";
    public String ROUTING_KEY = "monetarys.rk";

    public TransferFeedback getTransferRequestWithData() {
        TransferFeedback transferFeedback = new TransferFeedback();

        Header header = new Header();
        header.setMessageId(MESSAGE_ID);
        header.setTransactionId(TRANSACTION_ID);
        header.setOriginator(ORIGINATOR);
        header.setMessageToPayee(MESSAGE_TO_PAYEE);
        header.setMessageDatetime(LocalDateTime.now());
        header.setTransactionDatetime(LocalDateTime.now());

        IbsoData ibsoData = new IbsoData();
        ibsoData.setFirstName(FIRST_NAME);
        ibsoData.setLastName(LAST_NAME);
        Receiver receiver = new Receiver();
        receiver.setMessageId(MESSAGE_ID);
        receiver.setOriginator(ORIGINATOR);
        receiver.setAccountNumber(ACCOUNT_NUMBER);

        Sender sender = new Sender();
        sender.setMessageId(MESSAGE_ID);
        sender.setOriginator(ORIGINATOR);
        sender.setAccountNumber(ACCOUNT_NUMBER);

        transferFeedback.setHeader(header);
        transferFeedback.setIbsoData(ibsoData);
        transferFeedback.setReceiver(receiver);
        transferFeedback.setSender(sender);
        return transferFeedback;
    }

}
