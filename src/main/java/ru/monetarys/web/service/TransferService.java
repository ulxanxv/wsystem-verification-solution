package ru.monetarys.web.service;

import ru.monetarys.web.ro.TransferRequestRo;
import ru.monetarys.web.ro.TransferResponseRo;

public interface TransferService {

    TransferResponseRo transferMoney(TransferRequestRo transferRequestRo);

}
