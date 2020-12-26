package ru.monetarys.logic;

import ru.monetarys.dao.models.Transfer;
import ru.monetarys.domain.web.TransferRequest;

public interface TransferManager {

    Transfer transferMoney(TransferRequest transferRequest);

}
