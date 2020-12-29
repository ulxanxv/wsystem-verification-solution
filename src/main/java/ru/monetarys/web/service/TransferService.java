package ru.monetarys.web.service;

import ru.monetarys.ro.web.TransferRequestRo;
import ru.monetarys.ro.web.TransferResponseRo;

public interface TransferService {

    TransferResponseRo transferMoney(TransferRequestRo transferRequestRo);

}
