package ru.monetarys.integration.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.monetarys.integration.ApplicationProperties;
import ru.monetarys.integration.domain.ClientGeneralInfo;
import ru.monetarys.integration.mapper.ClientProfileRsMapper;
import ru.monetarys.integration.rs.ClientGeneralInfoRs;
import ru.monetarys.exceptions.ClientException;
import ru.monetarys.exceptions.ClientErrorCode;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class ClientProfileServiceImpl implements ClientProfileService {

    private final RestTemplate restTemplate;
    private final ApplicationProperties applicationProperties;
    private final ClientProfileRsMapper clientProfileRsMapper;

    public ClientGeneralInfo getClientInfoByGuid(@NonNull String guid) {
        URI uri = UriComponentsBuilder.fromHttpUrl(applicationProperties.getClientProfileService().getHost())
                .path(applicationProperties.getClientProfileService().getClientInfoByGuidPath())
                .queryParam("clientGUID", guid)
                .build()
                .toUri();

        ResponseEntity<ClientGeneralInfoRs> response =
                restTemplate.getForEntity(uri, ClientGeneralInfoRs.class);

        ClientGeneralInfoRs clientInfo = response.getBody();

        if (response.getStatusCode() != HttpStatus.OK || clientInfo == null) {
            throw new ClientException(ClientErrorCode.PROFILE_NOT_FOUND, guid);
        }

        if (clientInfo.getAccountList() == null || clientInfo.getAccountList().size() == 0) {
            throw new ClientException(ClientErrorCode.ACCOUNT_NOT_FOUND, guid);
        }

        return clientProfileRsMapper.to(clientInfo);
    }

}
