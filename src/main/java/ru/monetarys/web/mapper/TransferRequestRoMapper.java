package ru.monetarys.web.mapper;

import org.mapstruct.Mapper;
import ru.monetarys.web.domain.Payee;
import ru.monetarys.web.domain.Payer;
import ru.monetarys.web.domain.Transaction;
import ru.monetarys.web.domain.TransferRequest;
import ru.monetarys.web.ro.PayeeRo;
import ru.monetarys.web.ro.PayerRo;
import ru.monetarys.web.ro.TransactionRo;
import ru.monetarys.web.ro.TransferRequestRo;

@Mapper(componentModel = "spring")
public interface TransferRequestRoMapper {

    TransferRequest to(TransferRequestRo source);

    Transaction to(TransactionRo source);

    Payer to(PayerRo source);

    Payee to(PayeeRo source);

}
