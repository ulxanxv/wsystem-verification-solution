package ru.monetarys.integration.service;

import ru.monetarys.integration.domain.ClientGeneralInfo;

public interface ClientProfileService {

    ClientGeneralInfo getClientInfoByGuid(String guid);

}
