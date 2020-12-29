package ru.monetarys.web.mapper;

import org.mapstruct.Mapper;
import ru.monetarys.domain.web.Payee;
import ru.monetarys.domain.web.Payer;
import ru.monetarys.domain.web.Transaction;
import ru.monetarys.domain.web.TransferRequest;
import ru.monetarys.ro.web.TransferRequestRo;

@Mapper(componentModel = "spring")
public interface TransferRequestRoMapper {

    TransferRequest to(TransferRequestRo source);

    Transaction to(TransferRequestRo.TransactionRo source);

    Payer to(TransferRequestRo.PayerRo source);

    Payee to(TransferRequestRo.PayeeRo source);

}
