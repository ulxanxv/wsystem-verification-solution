package ru.monetarys.logic.impl;

import ru.monetarys.integration.domain.ClientAccountInfo;
import ru.monetarys.integration.domain.ClientGeneralInfo;
import ru.monetarys.web.domain.TransferRequest;
import ru.monetarys.web.ro.TransferResponseRo;

public interface TransferManager {

    TransferResponseRo transferMoney(TransferRequest transferRequest);

    void validateData(TransferRequest transferRequest, ClientGeneralInfo payee, ClientGeneralInfo payer, ClientAccountInfo payerAccount, ClientAccountInfo payeeAccount);

    void validatePayer(TransferRequest transferRequest, ClientAccountInfo payerAccount);

    void validatePayee(ClientGeneralInfo payee, ClientGeneralInfo payer, ClientAccountInfo payeeAccount, ClientAccountInfo payerAccount);

}
