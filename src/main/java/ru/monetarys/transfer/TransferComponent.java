package ru.monetarys.transfer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.monetarys.dto.ClientAccountInfo;
import ru.monetarys.dto.ClientGeneralInfo;
import ru.monetarys.exceptions.ClientErrorCode;
import ru.monetarys.exceptions.ClientException;
import ru.monetarys.exceptions.TransferErrorCode;
import ru.monetarys.models.Transfer;
import ru.monetarys.repositories.TransferRepository;
import ru.monetarys.services.clientprofile.ApplicationProperties;
import ru.monetarys.services.clientprofile.ClientProfileServiceImpl;
import ru.monetarys.transfer.entities.TransferRequest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@Component
public class TransferComponent {

    @Autowired
    private ClientProfileServiceImpl clientProfileService;

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private ApplicationProperties applicationProperties;

    public Transfer transferMoney(TransferRequest transferRequest) {
        return validateDataAndSave(transferRequest);
    }

    public Transfer saveTransfer(TransferRequest transferRequest, ClientGeneralInfo payer, ClientGeneralInfo payee, ClientAccountInfo payeeAccount) {
        return transferRepository.save(TransferUtil.getTransfer(applicationProperties, transferRequest, payer, payee, payeeAccount));
    }

    private Transfer validateDataAndSave(TransferRequest transferRequest) {
        validateUserRequest(transferRequest);

        ClientGeneralInfo payer = clientProfileService.getClientInfoByGUID(transferRequest.getPayer().getGuid());
        ClientGeneralInfo payee = clientProfileService.getClientInfoByGUID(transferRequest.getPayee().getGuid());
        ClientAccountInfo payerAccount = this.getAccount(payer.getAccountList(), transferRequest.getPayer().getAccount(), transferRequest.getPayer().getGuid());
        ClientAccountInfo payeeAccount = this.getAccount(payee.getAccountList(), transferRequest.getPayee().getAccount(), transferRequest.getPayee().getGuid());

        validatePayer(transferRequest, payerAccount);
        validatePayee(payee, payer, payeeAccount, payerAccount);

        return saveTransfer(transferRequest, payer, payee, payeeAccount);
    }

    private void validatePayer(TransferRequest transferRequest, ClientAccountInfo payerAccount) {
        TransferValidateHelper.newInstance()
                .validate(
                        applicationProperties.getTransferMoney().getAvailablePayerAccountStatuses(),
                        x -> Arrays.asList(x).contains(payerAccount.getAccountStatus()),
                        TransferErrorCode.INCORRECT_PAYER_ACCOUNT_STATUS)
                .validate(
                        applicationProperties.getTransferMoney().getAvailablePayerAccountTypes(),
                        x -> Arrays.asList(x).contains(payerAccount.getAccountType()),
                        TransferErrorCode.INCORRECT_PAYER_ACCOUNT_TYPE)
                .validate(
                        applicationProperties.getTransferMoney().getAvailableAccountCurrencies(),
                        x -> Arrays.asList(x).contains(payerAccount.getCurrency()),
                        TransferErrorCode.INCORRECT_PAYER_ACCOUNT_CURRENCY)
                .validate(
                        payerAccount.getBalance(),
                        x -> x.compareTo(transferRequest.getTransaction().getAmount()) > 0,
                        TransferErrorCode.BALANCE_EXCEED
                ).throwIfNotValid();
    }

    private void validatePayee(ClientGeneralInfo payee, ClientGeneralInfo payer, ClientAccountInfo payeeAccount, ClientAccountInfo payerAccount) {
        TransferValidateHelper.newInstance()
                .validate(
                        applicationProperties.getTransferMoney().getAvailablePayeeAccountStatuses(),
                        x -> Arrays.asList(x).contains(payeeAccount.getAccountStatus()),
                        TransferErrorCode.INCORRECT_PAYEE_ACCOUNT_STATUS)
                .validate(
                        applicationProperties.getTransferMoney().getAvailablePayeeAccountTypes(),
                        x -> Arrays.asList(x).contains(payeeAccount.getAccountType()),
                        TransferErrorCode.INCORRECT_PAYEE_ACCOUNT_TYPE)
                .validate(
                        applicationProperties.getTransferMoney().getAvailableAccountCurrencies(),
                        x -> Arrays.asList(x).contains(payeeAccount.getCurrency()),
                        TransferErrorCode.INCORRECT_PAYEE_ACCOUNT_CURRENCY)
                .validate(
                        applicationProperties.getTransferMoney().getAvailableCitizenshipCountryCode(),
                        x -> x.equals(payer.getPersonalInfo().getCitizenshipCountryCode()),
                        TransferErrorCode.PAYEE_NOT_RESIDENT)
                .validate(
                        payee.getPersonalInfo(),
                        x -> x.getFirstName().isEmpty() || x.getLastName().isEmpty(),
                        TransferErrorCode.PAYEE_NON_INDIVIDUAL)
                .validate(
                        payerAccount.getCurrency(),
                        x -> x.equals(payeeAccount.getCurrency()),
                        TransferErrorCode.CURRENCIES_NOT_MATCHING
                ).throwIfNotValid();
    }

    private void validateUserRequest(TransferRequest transferRequest) {
        TransferValidateHelper.newInstance().validate(
                        transferRequest.getPayer().getAccount().length(),
                        x -> (x < 16 || x > 22),
                        TransferErrorCode.PAYER_ACCOUNT_NOT_VALID)
                .validate(
                        transferRequest.getPayee().getGuid().length(),
                        x -> (x > 36),
                        TransferErrorCode.PAYEE_GUID_NOT_VALID)
                .validate(
                        transferRequest.getPayee().getAccountId().length(),
                        x -> (x > 36),
                        TransferErrorCode.PAYEE_ACC_ID_NOT_VALID)
                .validate(
                        transferRequest.getPayee().getAccount().length(),
                        x -> (x < 16 || x > 22),
                        TransferErrorCode.PAYEE_ACCOUNT_NOT_VALID)
                .validate(
                        transferRequest.getPayee().getAccount(),
                        x -> (Pattern.matches("40111\\d{15}", x) || Pattern.matches("40222\\d{15}", x) || Pattern.matches("40333\\d{15}", x)),
                        TransferErrorCode.PAYEE_ACCOUNT_NOT_ALLOWED)
                .validate(
                        transferRequest.getPayee().getCardNumber().length(),
                        x -> (x > 20),
                        TransferErrorCode.PAYEE_CARD_NUMBER_NOT_VALID)
                .validate(
                        transferRequest.getPayee().getPhone(),
                        x -> !Pattern.matches("^[0-9]{10}$", x),
                        TransferErrorCode.PAYEE_PHONE_NOT_VALID)
                .validate(
                        transferRequest.getTransaction().getAmount(),
                        x -> x.compareTo(new BigDecimal("0.0")) < 0,
                        TransferErrorCode.AMOUNT_NOT_VALID)
                .validate(
                        transferRequest.getTransaction().getMessageToPayee().length(),
                        x -> (x > 1024),
                        TransferErrorCode.MESSAGE_TO_PAYEE_NOT_VALID
                ).throwIfNotValid();
    }

    private ClientAccountInfo getAccount(List<ClientAccountInfo> accounts, String account, String guid) {
        return accounts.stream()
                .filter(x -> x.getAccountId().equals(account))
                .findFirst()
                .orElseThrow(() -> new ClientException(ClientErrorCode.ACCOUNT_NOT_FOUND, guid));
    }

}
