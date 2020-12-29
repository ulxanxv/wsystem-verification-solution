package ru.monetarys.integration.mapper;

import org.mapstruct.Mapper;
import ru.monetarys.domain.integration.ClientAccountInfo;
import ru.monetarys.domain.integration.ClientContactsInfo;
import ru.monetarys.domain.integration.ClientGeneralInfo;
import ru.monetarys.domain.integration.ClientPersonalInfo;
import ru.monetarys.rs.integration.ClientAccountInfoRs;
import ru.monetarys.rs.integration.ClientContactsInfoRs;
import ru.monetarys.rs.integration.ClientGeneralInfoRs;
import ru.monetarys.rs.integration.ClientPersonalInfoRs;

@Mapper(componentModel = "spring")
public interface ClientProfileRsMapper {

    ClientGeneralInfo to(ClientGeneralInfoRs source);

    ClientPersonalInfo to(ClientPersonalInfoRs source);

    ClientContactsInfo to(ClientContactsInfoRs source);

    ClientAccountInfo to(ClientAccountInfoRs source);

}
