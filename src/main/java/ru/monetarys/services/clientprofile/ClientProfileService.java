package ru.monetarys.services.clientprofile;

import ru.monetarys.dto.ClientGeneralInfo;

public interface ClientProfileService {

    ClientGeneralInfo getClientInfoByGUID(String guid);

}
