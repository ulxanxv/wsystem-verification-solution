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
    private final ClientProfileProperties apiProperties;

    public ClientGeneralInfo getClientInfoByGUID(@NonNull String guid) {
        URI uri = UriComponentsBuilder.fromHttpUrl(apiProperties.getClientProfileService().getHost())
                .path(apiProperties.getClientProfileService().getClientInfoByGuidPath())
                .queryParam("clientGUID", guid)
                .build()
                .toUri();

        ResponseEntity<ClientGeneralInfo> response =
                restTemplate.getForEntity(uri.toString(), ClientGeneralInfo.class);

        ClientGeneralInfo clientInfo;

        if ((response.getStatusCode() != HttpStatus.OK) || (clientInfo = response.getBody()) == null) {
            throw new ClientException(ErrorCode.PROFILE_NOT_FOUND, "Не найден профиль клиента", guid);
        }

        if ((clientInfo.getAccountList() == null) || (clientInfo.getAccountList().size() == 0)) {
            throw new ClientException(ErrorCode.ACCOUNT_NOT_FOUND, "Не найден ни один счёт клиента", guid);
        }

        return clientInfo;
    }

}
