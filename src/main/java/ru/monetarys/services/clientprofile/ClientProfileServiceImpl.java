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
import ru.monetarys.exceptions.AccountNotFound;
import ru.monetarys.exceptions.ClientProfileNotFound;
import ru.monetarys.interceptors.HeaderRequestInterceptor;

import java.util.Collections;
import java.util.Objects;

@Data
@Service
@RequiredArgsConstructor
public class ClientProfileServiceImpl implements ClientProfileService {

    private final RestTemplate restTemplate;
    private final ClientProfileApiProperties apiProperties;

    public ClientGeneralInfo getClientInfoByGUID(@NonNull String guid) {
        restTemplate.getInterceptors().add(new HeaderRequestInterceptor("Authorization", "Basic " + apiProperties.getSecret()));

        ResponseEntity<ClientGeneralInfo> response = restTemplate.getForEntity(
                UriComponentsBuilder.newInstance().scheme("http").host(apiProperties.getHost()).path(apiProperties.getClientInfoByGuidPath()).build().toUriString(),
                ClientGeneralInfo.class,
                Collections.singletonMap("guid", guid)
        );

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new ClientProfileNotFound(ClientProfileNotFound.ErrorCode.PROFILE_NOT_FOUND, "Не найден профиль клиента", guid);
        }

        ClientGeneralInfo clientInfo;

        if ((clientInfo = response.getBody()) == null) {
           return null;
        }

        if ((Objects.requireNonNull(clientInfo).getAccountList() == null) || (clientInfo.getAccountList().size() == 0)) {
            throw new AccountNotFound(AccountNotFound.ErrorCode.ACCOUNT_NOT_FOUND, "Не найден ни один счёт клиента", guid);
        }

        return clientInfo;
    }

}
