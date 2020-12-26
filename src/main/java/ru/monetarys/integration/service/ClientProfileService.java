package ru.monetarys.integration.service;

import ru.monetarys.domain.integration.ClientGeneralInfo;

public interface ClientProfileService {

    ClientGeneralInfo getClientInfoByGuid(String guid);

}
