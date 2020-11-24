package ru.monetarys.service;

import lombok.Data;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.monetarys.dto.GetClientInfoResponse;

import java.util.Collections;

@Data
@Service
public class ClientProfileService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String HOST = "http://clientprofile.internal.anybank.ru";

    public GetClientInfoResponse getClientInfoByGUID(@NonNull String guid) {
        return restTemplate.getForObject(
                HOST + "/v1/clientInfoByGUID/{guid}",
                GetClientInfoResponse.class,
                Collections.singletonMap("guid", guid)
        );
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
