package ru.monetarys.services.clientprofile;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.monetarys.dto.ClientGeneralInfo;
import ru.monetarys.exceptions.ClientException;
import ru.monetarys.exceptions.ErrorCode;

import java.net.URI;

@Data
@Service
@RequiredArgsConstructor
public class ClientProfileServiceImpl implements ClientProfileService {

    private final RestTemplate restTemplate;
    private final ApplicationProperties applicationProperties;

    public ClientGeneralInfo getClientInfoByGUID(@NonNull String guid) {
        URI uri = UriComponentsBuilder.fromHttpUrl(applicationProperties.getClientProfileService().getHost())
                .path(applicationProperties.getClientProfileService().getClientInfoByGuidPath())
                .queryParam("clientGUID", guid)
                .build()
                .toUri();

        ResponseEntity<ClientGeneralInfo> response =
                restTemplate.getForEntity(uri, ClientGeneralInfo.class);

        ClientGeneralInfo clientInfo = response.getBody();

        if ((response.getStatusCode() != HttpStatus.OK) || (clientInfo) == null) {
            throw new ClientException(ErrorCode.PROFILE_NOT_FOUND, guid);
        }

        if ((clientInfo.getAccountList() == null) || (clientInfo.getAccountList().size() == 0)) {
            throw new ClientException(ErrorCode.ACCOUNT_NOT_FOUND, guid);
        }

        return clientInfo;
    }

}
