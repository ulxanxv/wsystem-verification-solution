package ru.monetarys.web;

import lombok.experimental.UtilityClass;
import ru.monetarys.integration.ApplicationProperties;
import ru.monetarys.integration.domain.ClientAccountInfo;
import ru.monetarys.integration.domain.ClientGeneralInfo;
import ru.monetarys.models.Transfer;
import ru.monetarys.models.TransferStatus;
import ru.monetarys.web.domain.TransferRequest;
import ru.monetarys.web.ro.PayeeAccountResponseRo;
import ru.monetarys.web.ro.PayerAccountResponseRo;
import ru.monetarys.web.ro.TransactionResponseRo;
import ru.monetarys.web.ro.TransferResponseRo;

import java.util.UUID;

@UtilityClass
public class TransferUtil {

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

    public TransferResponseRo getTransferResponseRo(Transfer transfer) {
        TransactionResponseRo transaction = TransactionResponseRo.builder()
                .amount(transfer.getAmount())
                .currency(transfer.getCurrency())
                .id(transfer.getTransactionId())
                .status(transfer.getStatus())
                .build();

        return TransferResponseRo.builder()
                .transaction(transaction)
                .payerAccount(new PayerAccountResponseRo(transfer.getPayerAccount().substring(transfer.getPayerAccount().length() - 5)))
                .payeeAccount(new PayeeAccountResponseRo(transfer.getPayeeAccount().substring(transfer.getPayeeAccount().length() - 5)))
                .build();
    }

}
