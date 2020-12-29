package ru.monetarys.logic;

import ru.monetarys.dao.models.Transfer;
import ru.monetarys.domain.web.TransferRequest;
import ru.monetarys.integration.messages.entities.TransferFeedback;

public interface TransferManager {

    Transfer transferMoney(TransferRequest transferRequest);

    void updateTransferStatus(TransferFeedback transfer);

}
