package ru.monetarys.logic;

import ru.monetarys.dao.models.Transfer;
import ru.monetarys.domain.web.TransferHistoryFilterRequest;
import ru.monetarys.domain.web.TransferRequest;
import ru.monetarys.integration.messages.entities.TransferFeedback;

import java.util.List;

public interface TransferManager {

    Transfer transferMoney(TransferRequest transferRequest);

    List<Transfer> transferHistoryByFilter(TransferHistoryFilterRequest filter);

    void updateTransferStatus(TransferFeedback transfer);

}
