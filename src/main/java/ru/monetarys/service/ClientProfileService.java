package ru.monetarys.service;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.monetarys.dto.GetClientInfoResponse;

import java.util.Collections;
import java.util.Objects;

@Data
@Service
@Slf4j
public class ClientProfileService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${service.host}")
    private String HOST;

    public GetClientInfoResponse getClientInfoByGUID(@NonNull String guid) {
        ResponseEntity<GetClientInfoResponse> response = restTemplate.getForEntity(
                HOST + "/v1/clientInfoByGUID/{guid}",
                GetClientInfoResponse.class,
                Collections.singletonMap("guid", guid)
        );

        if (response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            log.error("Profile not found : не найден профиль клиента с идентификатором — " + guid);
        }

        GetClientInfoResponse clientInfo = response.getBody();

        if (Objects.requireNonNull(clientInfo).getClientGeneralInfo().getAccountList().size() > 0) {
            log.error("Account not found : не найден счёт клиента с идентификатором — " + guid);
        }

        return clientInfo;
    }

    public GetClientInfoResponse getClientInfoByPhone(@NonNull String phone) {
        return restTemplate.getForObject(
                HOST + "/v1/clientInfoByPhone/{phone}",
                GetClientInfoResponse.class,
                Collections.singletonMap("phone", phone)
        );
    }

    public GetClientInfoResponse getClientInfoByAccount(@NonNull String account) {
        return restTemplate.getForObject(
                HOST + "/v1/clientInfoByAccount/{account}",
                GetClientInfoResponse.class,
                Collections.singletonMap("account", account)
        );
    }

    public GetClientInfoResponse clientInfoByCard(@NonNull String card) {
        return restTemplate.getForObject(
                HOST + "/v1/clientInfoByCard/{card}",
                GetClientInfoResponse.class,
                Collections.singletonMap("card", card)
        );
    }

}
