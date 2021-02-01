package ru.monetarys.logic.impl;

import com.querydsl.core.types.Predicate;
import liquibase.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.monetarys.config.ApplicationProperties;
import ru.monetarys.dao.models.Transfer;
import ru.monetarys.dao.models.TransferStatus;
import ru.monetarys.dao.repositories.TransferRepository;
import ru.monetarys.domain.integration.ClientAccountInfo;
import ru.monetarys.domain.integration.ClientGeneralInfo;
import ru.monetarys.domain.web.TransferHistoryFilterRequest;
import ru.monetarys.domain.web.TransferRequest;
import ru.monetarys.exceptions.*;
import ru.monetarys.integration.messages.entities.TransferFeedback;
import ru.monetarys.integration.service.ClientProfileService;
import ru.monetarys.logic.TransferManager;
import ru.monetarys.util.QPredicateUtil;
import ru.monetarys.web.helper.TransferValidateHelper;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static ru.monetarys.dao.models.QTransfer.transfer;

@Component
@RequiredArgsConstructor
public class TransferManagerImpl implements TransferManager {

    private final ClientProfileService clientProfileService;
    private final ApplicationProperties applicationProperties;
    private final TransferRepository transferRepository;

    @Override
    public Transfer transferMoney(TransferRequest transferRequest) {
        ClientGeneralInfo payer = clientProfileService.getClientInfoByGuid(transferRequest.getPayer().getGuid());
        ClientGeneralInfo payee = clientProfileService.getClientInfoByGuid(transferRequest.getPayee().getGuid());

        ClientAccountInfo payerAccount = this.getAccount(
                payer.getAccountList(), transferRequest.getPayer().getAccount(), transferRequest.getPayer().getGuid()
        );
        ClientAccountInfo payeeAccount = this.getAccount(
                payee.getAccountList(), transferRequest.getPayee().getAccount(), transferRequest.getPayee().getGuid()
        );

        validateData(transferRequest, payee, payer, payerAccount, payeeAccount);

        return saveTransfer(transferRequest, payer, payee, payeeAccount);
    }

    @Override
    public List<Transfer> transferHistoryByFilter(TransferHistoryFilterRequest filter) {
        Predicate predicate = QPredicateUtil.builder()
                .add(
                        filter.getPeriod(),
                        x -> transfer.creationDate.between(x.getFrom(), x.getTo())
                ).add(
                        filter.getAmount(),
                        x -> transfer.amount.between(x.getFrom(), x.getTo())
                ).add(
                        filter.getPayerAccount(),
                        transfer.payerAccount::eq
                ).add(
                        this.getTransferStatusesListIfExists(filter.getStatuses()),
                        transfer.status::in
                ).buildOr();

        return transferRepository.findAll(predicate, Pageable.unpaged()).getContent();
    }

    @Override
    public void updateTransferStatus(TransferFeedback transferFeedback) {
        String transactionId = transferFeedback.getHeader().getTransactionId();
        Transfer byTransactionId = transferRepository
                .findByTransactionId(transactionId)
                .orElseThrow(
                        () -> new TransferNotFoundException("Информация о переводе " + transactionId + " не найдена!")
                );

        byTransactionId.setStatus(transferFeedback.getHeader().getTransactionStatus());
        transferRepository.save(byTransactionId);
    }

    private Transfer saveTransfer(TransferRequest transferRequest, ClientGeneralInfo payer,
                                  ClientGeneralInfo payee, ClientAccountInfo payeeAccount
    ) {
        return transferRepository.save(
                this.getTransfer(applicationProperties, transferRequest, payer, payee, payeeAccount)
        );
    }

    private void validateData(
            TransferRequest transferRequest, ClientGeneralInfo payee,
            ClientGeneralInfo payer, ClientAccountInfo payerAccount, ClientAccountInfo payeeAccount
    ) {
        validatePayer(transferRequest, payerAccount);
        validatePayee(payee, payer, payeeAccount, payerAccount);
    }

    private void validatePayer(TransferRequest transferRequest, ClientAccountInfo payerAccount) {
        TransferValidateHelper.newInstance()
                .validate(
                    applicationProperties.getTransferMoney().getAvailablePayerAccountStatuses(),
                    x -> x.contains(payerAccount.getAccountStatus()),
                    TransferErrorCode.INCORRECT_PAYER_ACCOUNT_STATUS
                ).validate(
                    applicationProperties.getTransferMoney().getAvailablePayerAccountTypes(),
                    x -> x.contains(payerAccount.getAccountType()),
                    TransferErrorCode.INCORRECT_PAYER_ACCOUNT_TYPE
                ).validate(
                    applicationProperties.getTransferMoney().getAvailableAccountCurrencies(),
                    x -> x.contains(payerAccount.getCurrency()),
                    TransferErrorCode.INCORRECT_PAYER_ACCOUNT_CURRENCY
                ).validate(
                    payerAccount.getBalance(),
                    x -> x.compareTo(transferRequest.getTransaction().getAmount()) > 0,
                    TransferErrorCode.BALANCE_EXCEED
                ).throwIfNotValid(TransferIntegrationValidateException.class);
    }

    private void validatePayee(
            ClientGeneralInfo payee, ClientGeneralInfo payer,
            ClientAccountInfo payeeAccount, ClientAccountInfo payerAccount
    ) {
        TransferValidateHelper.newInstance()
                .validate(
                    applicationProperties.getTransferMoney().getAvailablePayeeAccountStatuses(),
                    x -> x.contains(payeeAccount.getAccountStatus()),
                    TransferErrorCode.INCORRECT_PAYEE_ACCOUNT_STATUS
                ).validate(
                    applicationProperties.getTransferMoney().getAvailablePayeeAccountTypes(),
                    x -> x.contains(payeeAccount.getAccountType()),
                    TransferErrorCode.INCORRECT_PAYEE_ACCOUNT_TYPE
                ).validate(
                    applicationProperties.getTransferMoney().getAvailableAccountCurrencies(),
                    x -> x.contains(payeeAccount.getCurrency()),
                    TransferErrorCode.INCORRECT_PAYEE_ACCOUNT_CURRENCY
                ).validate(
                    applicationProperties.getTransferMoney().getAvailableCitizenshipCountryCode(),
                    x -> x.equals(payer.getPersonalInfo().getCitizenshipCountryCode()),
                    TransferErrorCode.PAYEE_NOT_RESIDENT
                ).validate(
                    payee.getPersonalInfo(),
                    x -> StringUtils.isEmpty(x.getFirstName()) || StringUtils.isEmpty(x.getLastName()),
                    TransferErrorCode.PAYEE_NON_INDIVIDUAL
                ).validate(
                    payerAccount.getCurrency(),
                    x -> x.equals(payeeAccount.getCurrency()),
                    TransferErrorCode.CURRENCIES_NOT_MATCHING
                ).throwIfNotValid(TransferIntegrationValidateException.class);
    }

    private List<TransferStatus> getTransferStatusesListIfExists(List<String> statuses) {
        if (statuses != null) {
            return statuses.stream()
                    .map(TransferStatus::byName)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }

    private ClientAccountInfo getAccount(List<ClientAccountInfo> accounts, String account, String guid) {
        return accounts.stream()
                .filter(x -> x.getAccountId().equals(account))
                .findFirst()
                .orElseThrow(() -> new ClientException(ClientErrorCode.ACCOUNT_NOT_FOUND, guid));
    }

    private Transfer getTransfer(
            ApplicationProperties applicationProperties, TransferRequest transferRequest,
            ClientGeneralInfo payer, ClientGeneralInfo payee, ClientAccountInfo payeeAccount
    ) {
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

}
