package ru.monetarys.logic.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.monetarys.exceptions.ClientErrorCode;
import ru.monetarys.exceptions.ClientException;
import ru.monetarys.exceptions.TransferErrorCode;
import ru.monetarys.integration.ApplicationProperties;
import ru.monetarys.integration.domain.ClientAccountInfo;
import ru.monetarys.integration.domain.ClientGeneralInfo;
import ru.monetarys.integration.service.ClientProfileService;
import ru.monetarys.logic.impl.TransferManager;
import ru.monetarys.models.Transfer;
import ru.monetarys.repositories.TransferRepository;
import ru.monetarys.web.TransferUtil;
import ru.monetarys.web.domain.TransferRequest;
import ru.monetarys.web.helper.TransferValidateHelper;
import ru.monetarys.web.ro.TransferResponseRo;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TransferManagerImpl implements TransferManager {

    private final ClientProfileService clientProfileService;
    private final ApplicationProperties applicationProperties;
    private final TransferRepository transferRepository;

    @Override
    public TransferResponseRo transferMoney(TransferRequest transferRequest) {
        ClientGeneralInfo payer         = clientProfileService.getClientInfoByGuid(transferRequest.getPayer().getGuid());
        ClientGeneralInfo payee         = clientProfileService.getClientInfoByGuid(transferRequest.getPayee().getGuid());
        ClientAccountInfo payerAccount  = this.getAccount(payer.getAccountList(), transferRequest.getPayer().getAccount(), transferRequest.getPayer().getGuid());
        ClientAccountInfo payeeAccount  = this.getAccount(payee.getAccountList(), transferRequest.getPayee().getAccount(), transferRequest.getPayee().getGuid());

        validateData(transferRequest, payee, payer, payerAccount, payeeAccount);

        return saveTransfer(transferRequest, payer, payee, payeeAccount);
    }

    public void validateData(TransferRequest transferRequest, ClientGeneralInfo payee, ClientGeneralInfo payer, ClientAccountInfo payerAccount, ClientAccountInfo payeeAccount) {
        validatePayer(transferRequest, payerAccount);
        validatePayee(payee, payer, payeeAccount, payerAccount);
    }

    public TransferResponseRo saveTransfer(TransferRequest transferRequest, ClientGeneralInfo payer, ClientGeneralInfo payee, ClientAccountInfo payeeAccount) {
        Transfer savedObject = transferRepository.save(TransferUtil.getTransfer(applicationProperties, transferRequest, payer, payee, payeeAccount));
        return TransferUtil.getTransferResponseRo(savedObject);
    }

    @Override
    public void validatePayer(TransferRequest transferRequest, ClientAccountInfo payerAccount) {
        TransferValidateHelper.newInstance()
                .validate(
                    applicationProperties.getTransferMoney().getAvailablePayerAccountStatuses(),
                    x -> Arrays.asList(x).contains(payerAccount.getAccountStatus()),
                    TransferErrorCode.INCORRECT_PAYER_ACCOUNT_STATUS
                ).validate(
                    applicationProperties.getTransferMoney().getAvailablePayerAccountTypes(),
                    x -> Arrays.asList(x).contains(payerAccount.getAccountType()),
                    TransferErrorCode.INCORRECT_PAYER_ACCOUNT_TYPE
                ).validate(
                    applicationProperties.getTransferMoney().getAvailableAccountCurrencies(),
                    x -> Arrays.asList(x).contains(payerAccount.getCurrency()),
                    TransferErrorCode.INCORRECT_PAYER_ACCOUNT_CURRENCY
                ).validate(
                    payerAccount.getBalance(),
                    x -> x.compareTo(transferRequest.getTransaction().getAmount()) > 0,
                    TransferErrorCode.BALANCE_EXCEED
                ).throwIfNotValid();
    }

    @Override
    public void validatePayee(ClientGeneralInfo payee, ClientGeneralInfo payer, ClientAccountInfo payeeAccount, ClientAccountInfo payerAccount) {
        TransferValidateHelper.newInstance()
                .validate(
                    applicationProperties.getTransferMoney().getAvailablePayeeAccountStatuses(),
                    x -> Arrays.asList(x).contains(payeeAccount.getAccountStatus()),
                    TransferErrorCode.INCORRECT_PAYEE_ACCOUNT_STATUS
                ).validate(
                    applicationProperties.getTransferMoney().getAvailablePayeeAccountTypes(),
                    x -> Arrays.asList(x).contains(payeeAccount.getAccountType()),
                    TransferErrorCode.INCORRECT_PAYEE_ACCOUNT_TYPE
                ).validate(
                    applicationProperties.getTransferMoney().getAvailableAccountCurrencies(),
                    x -> Arrays.asList(x).contains(payeeAccount.getCurrency()),
                    TransferErrorCode.INCORRECT_PAYEE_ACCOUNT_CURRENCY
                ).validate(
                    applicationProperties.getTransferMoney().getAvailableCitizenshipCountryCode(),
                    x -> x.equals(payer.getPersonalInfo().getCitizenshipCountryCode()),
                    TransferErrorCode.PAYEE_NOT_RESIDENT
                ).validate(
                    payee.getPersonalInfo(),
                    x -> x.getFirstName().isEmpty() || x.getLastName().isEmpty(),
                    TransferErrorCode.PAYEE_NON_INDIVIDUAL
                ).validate(
                    payerAccount.getCurrency(),
                    x -> x.equals(payeeAccount.getCurrency()),
                    TransferErrorCode.CURRENCIES_NOT_MATCHING
                ).throwIfNotValid();
    }

    private ClientAccountInfo getAccount(List<ClientAccountInfo> accounts, String account, String guid) {
        return accounts.stream()
                .filter(x -> x.getAccountId().equals(account))
                .findFirst()
                .orElseThrow(() -> new ClientException(ClientErrorCode.ACCOUNT_NOT_FOUND, guid));
    }

}
