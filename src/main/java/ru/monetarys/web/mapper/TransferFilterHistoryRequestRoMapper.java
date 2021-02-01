package ru.monetarys.web.mapper;

import org.mapstruct.Mapper;
import ru.monetarys.domain.web.Amount;
import ru.monetarys.domain.web.Period;
import ru.monetarys.domain.web.TransferHistoryFilterRequest;
import ru.monetarys.ro.web.TransferHistoryFilterRequestRo;

@Mapper(componentModel = "spring")
public interface TransferFilterHistoryRequestRoMapper {

    TransferHistoryFilterRequest to(TransferHistoryFilterRequestRo source);

    Period to(TransferHistoryFilterRequestRo.Period source);

    Amount to(TransferHistoryFilterRequestRo.Amount source);

}
