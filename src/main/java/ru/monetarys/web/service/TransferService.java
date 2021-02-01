package ru.monetarys.web.service;

import ru.monetarys.dao.models.Transfer;
import ru.monetarys.ro.web.TransferHistoryFilterRequestRo;
import ru.monetarys.ro.web.TransferRequestRo;
import ru.monetarys.ro.web.TransferResponseRo;

import java.util.List;

public interface TransferService {

    TransferResponseRo transferMoney(TransferRequestRo transferRequestRo);

    List<Transfer> transferHistoryByFilter(TransferHistoryFilterRequestRo filter);

}
