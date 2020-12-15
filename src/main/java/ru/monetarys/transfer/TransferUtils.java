package ru.monetarys.transfer;

import lombok.experimental.UtilityClass;
import ru.monetarys.dto.ClientAccountInfo;
import ru.monetarys.dto.ClientGeneralInfo;
import ru.monetarys.models.Transfer;
import ru.monetarys.models.TransferStatus;
import ru.monetarys.services.clientprofile.ApplicationProperties;
import ru.monetarys.transfer.entities.*;

import java.util.UUID;

@UtilityClass
public class TransferUtils {

    public Transfer getTransfer(ApplicationProperties applicationProperties, TransferRequest transferRequest, ClientGeneralInfo payer, ClientGeneralInfo payee, ClientAccountInfo payeeAccount) {
        return Transfer.builder()
                .operationCode(applicationProperties.getTransferMoney().getOperationCode())
                .transactionId(UUID.randomUUID().toString())
                .amount(transferRequest.getTransaction().getAmount())
                .currency(applicationProperties.getTransferMoney().getOperationCurrency())
                .description(applicationProperties.getTransferMoney().getTransferDescription())
                .messageToPayee(transferRequest.getTransaction().getMessageToPayee())
                .status(TransferStatus.NEW)
                .payerAccount(transferRequest.getPayer().getAccount())
                .payerLastName(payer.getPersonalInfo().getLastName())
                .payerFirstName(payer.getPersonalInfo().getFirstName())
                .payerSubName(payer.getPersonalInfo().getMiddleName())
                .payerPhone(payer.getContacts().getPhoneNumber())
                .payeeBank(payeeAccount.getBankName())
                .payeeAccount(transferRequest.getPayee().getAccount())
                .payeeLastName(payee.getPersonalInfo().getLastName())
                .payeeFirstName(payee.getPersonalInfo().getFirstName())
                .payeeSubName(payee.getPersonalInfo().getMiddleName())
                .payeePhone(payee.getContacts().getPhoneNumber())
                .build();
    }

    public TransferResponse getTransferResponse(Transfer transfer) {
        TransactionResponse transactionResponse = TransactionResponse.builder()
                .amount(transfer.getAmount())
                .currency(transfer.getCurrency())
                .id(transfer.getTransactionId())
                .transferStatus(transfer.getStatus())
                .build();

        return TransferResponse.builder()
                .transaction(transactionResponse)
                .payerAccount(new PayerAccount(transfer.getPayerAccount().substring(transfer.getPayerAccount().length() - 5)))
                .payeeAccount(new PayeeAccount(transfer.getPayeeAccount().substring(transfer.getPayeeAccount().length() - 5)))
                .build();
    }



}
