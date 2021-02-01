package ru.monetarys.web.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.monetarys.exceptions.TransferErrorCode;
import ru.monetarys.exceptions.TransferFilterValidateException;
import ru.monetarys.exceptions.TransferValidateException;
import ru.monetarys.config.ApplicationProperties;
import ru.monetarys.logic.TransferManager;
import ru.monetarys.dao.models.Transfer;
import ru.monetarys.ro.web.TransferHistoryFilterRequestRo;
import ru.monetarys.web.helper.TransferValidateHelper;
import ru.monetarys.web.mapper.TransferFilterHistoryRequestRoMapper;
import ru.monetarys.web.mapper.TransferRequestRoMapper;
import ru.monetarys.ro.web.TransferRequestRo;
import ru.monetarys.ro.web.TransferResponseRo;
import ru.monetarys.ro.web.TransferResponseRo.PayeeRo;
import ru.monetarys.ro.web.TransferResponseRo.PayerRo;
import ru.monetarys.ro.web.TransferResponseRo.TransactionRo;
import ru.monetarys.web.service.TransferService;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final TransferRequestRoMapper transferRequestRoMapper;
    private final TransferFilterHistoryRequestRoMapper transferFilterHistoryRequestRoMapper;

    private final ApplicationProperties applicationProperties;
    private final TransferManager transferManager;

    @Override
    @Transactional
    public TransferResponseRo transferMoney(TransferRequestRo transferRequestRo) {
        validateUserRequest(transferRequestRo);
        return this.getTransferResponseRo(
                transferManager.transferMoney(transferRequestRoMapper.to(transferRequestRo))
        );
    }

    @Override
    public List<Transfer> transferHistoryByFilter(TransferHistoryFilterRequestRo filter) {
        validateFilterRequest(filter);
        return transferManager.transferHistoryByFilter(
                transferFilterHistoryRequestRoMapper.to(filter)
        );
    }

    private void validateFilterRequest(TransferHistoryFilterRequestRo filter) {
        TransferValidateHelper.newInstance().validate(
                filter.getPayerAccount(),
                x -> !Pattern.matches("^\\d{16,22}$", x),
                TransferErrorCode.PAYER_ACCOUNT_NOT_VALID
            ).validate(
                filter.getAmount(),
                x -> x.getFrom().compareTo(BigDecimal.ZERO) <= 0 || x.getTo().compareTo(BigDecimal.ZERO) <= 0,
                TransferErrorCode.AMOUNT_NOT_VALID
            ).throwIfNotValid(TransferFilterValidateException.class);
    }

    private void validateUserRequest(TransferRequestRo transferRequestRo) {
        TransferValidateHelper.newInstance().validate(
                        transferRequestRo.getPayer().getAccount(),
                        x -> !Pattern.matches("^\\d{16,22}$", x),
                        TransferErrorCode.PAYER_ACCOUNT_NOT_VALID
                ).validate(
                        transferRequestRo.getPayee().getGuid().length(),
                        x -> x > 36,
                        TransferErrorCode.PAYEE_GUID_NOT_VALID
                ).validate(
                        transferRequestRo.getPayee().getAccountId().length(),
                        x -> x > 36,
                        TransferErrorCode.PAYEE_ACC_ID_NOT_VALID
                ).validate(
                        transferRequestRo.getPayee().getAccount(),
                        x -> !Pattern.matches("^\\d{16,22}$", x),
                        TransferErrorCode.PAYEE_ACCOUNT_NOT_VALID
                ).validate(
                        transferRequestRo.getPayee().getAccount(),
                        x -> applicationProperties.getTransferMoney().getUnavailableAccountsRegex()
                            .stream()
                            .anyMatch(e -> Pattern.matches(e, x)),
                        TransferErrorCode.PAYEE_ACCOUNT_NOT_ALLOWED
                ).validate(
                        transferRequestRo.getPayee().getCardNumber().length(),
                        x -> x > 20,
                        TransferErrorCode.PAYEE_CARD_NUMBER_NOT_VALID
                ).validate(
                        transferRequestRo.getPayee().getPhone(),
                        x -> !Pattern.matches(applicationProperties.getTransferMoney().getPhoneRegex(), x),
                        TransferErrorCode.PAYEE_PHONE_NOT_VALID
                ).validate(
                        transferRequestRo.getTransaction().getAmount(),
                        x -> x.compareTo(BigDecimal.ZERO) < 0,
                        TransferErrorCode.AMOUNT_NOT_VALID
                ).validate(
                        transferRequestRo.getTransaction().getMessageToPayee().length(),
                        x -> x > 1024,
                        TransferErrorCode.MESSAGE_TO_PAYEE_NOT_VALID
                ).throwIfNotValid(TransferValidateException.class);
    }

    private TransferResponseRo getTransferResponseRo(Transfer transfer) {
        TransferResponseRo.TransactionRo transactionRo = TransactionRo.builder()
                .amount(transfer.getAmount())
                .currency(transfer.getCurrency())
                .id(transfer.getTransactionId())
                .status(transfer.getStatus())
                .status(transfer.getStatus())
                .build();

        return TransferResponseRo.builder()
                .transaction(transactionRo)
                .payerAccount(new PayerRo(
                        transfer.getPayerAccount().substring(transfer.getPayerAccount().length() - 5))
                )
                .payeeAccount(new PayeeRo(
                        transfer.getPayeeAccount().substring(transfer.getPayeeAccount().length() - 5))
                )
                .build();
    }

}
