package ru.monetarys.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.monetarys.exceptions.TransferErrorCode;
import ru.monetarys.web.helper.TransferValidateHelper;
import ru.monetarys.logic.manager.TransferManagerImpl;
import ru.monetarys.web.mapper.TransferRequestRoMapper;
import ru.monetarys.web.ro.TransferRequestRo;
import ru.monetarys.web.ro.TransferResponseRo;

import java.math.BigDecimal;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final TransferRequestRoMapper transferRequestRoMapper;
    private final TransferManagerImpl transferManager;

    @Override
    @Transactional
    public TransferResponseRo transferMoney(TransferRequestRo transferRequestRo) {
        validateUserRequest(transferRequestRo);
        return transferManager.transferMoney(transferRequestRoMapper.to(transferRequestRo));
    }

    private void validateUserRequest(TransferRequestRo transferRequestRo) {
        TransferValidateHelper.newInstance().validate(
                        transferRequestRo.getPayer().getAccount().length(),
                        x -> x < 16 || x > 22,
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
                        transferRequestRo.getPayee().getAccount().length(),
                        x -> x < 16 || x > 22,
                        TransferErrorCode.PAYEE_ACCOUNT_NOT_VALID
                ).validate(
                        transferRequestRo.getPayee().getAccount(),
                        x -> Pattern.matches("40111\\d{15}", x) || Pattern.matches("40222\\d{15}", x) || Pattern.matches("40333\\d{15}", x),
                        TransferErrorCode.PAYEE_ACCOUNT_NOT_ALLOWED
                ).validate(
                        transferRequestRo.getPayee().getCardNumber().length(),
                        x -> x > 20,
                        TransferErrorCode.PAYEE_CARD_NUMBER_NOT_VALID
                ).validate(
                        transferRequestRo.getPayee().getPhone(),
                        x -> !Pattern.matches("^[0-9]{10}$", x),
                        TransferErrorCode.PAYEE_PHONE_NOT_VALID
                ).validate(
                        transferRequestRo.getTransaction().getAmount(),
                        x -> x.compareTo(new BigDecimal("0.0")) < 0,
                        TransferErrorCode.AMOUNT_NOT_VALID
                ).validate(
                        transferRequestRo.getTransaction().getMessageToPayee().length(),
                        x -> x > 1024,
                        TransferErrorCode.MESSAGE_TO_PAYEE_NOT_VALID
                ).throwIfNotValid();
    }

}
