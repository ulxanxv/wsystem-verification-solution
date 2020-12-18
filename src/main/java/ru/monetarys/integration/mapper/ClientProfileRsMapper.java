package ru.monetarys.integration.mapper;

import org.mapstruct.Mapper;
import ru.monetarys.integration.domain.ClientAccountInfo;
import ru.monetarys.integration.domain.ClientContactsInfo;
import ru.monetarys.integration.domain.ClientGeneralInfo;
import ru.monetarys.integration.domain.ClientPersonalInfo;
import ru.monetarys.integration.rs.ClientAccountInfoRs;
import ru.monetarys.integration.rs.ClientContactsInfoRs;
import ru.monetarys.integration.rs.ClientGeneralInfoRs;
import ru.monetarys.integration.rs.ClientPersonalInfoRs;

@Mapper(componentModel = "spring")
public interface ClientProfileRsMapper {

    ClientGeneralInfo to(ClientGeneralInfoRs source);

    ClientPersonalInfo to(ClientPersonalInfoRs source);

    ClientContactsInfo to(ClientContactsInfoRs source);

    ClientAccountInfo to(ClientAccountInfoRs source);

}
