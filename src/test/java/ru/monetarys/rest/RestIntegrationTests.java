package ru.monetarys;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;
import ru.monetarys.dto.ClientAccountInfo;
import ru.monetarys.dto.ClientGeneralInfo;
import ru.monetarys.exceptions.ClientException;
import ru.monetarys.exceptions.ErrorCode;
import ru.monetarys.services.clientprofile.ApplicationProperties;
import ru.monetarys.services.clientprofile.ClientProfileServiceImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;


@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class RestIntegrationTests {

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private ApplicationProperties properties;

    @MockBean
    private ClientProfileServiceImpl service;

    @BeforeEach
    public void setup() {
        service = new ClientProfileServiceImpl(restTemplate, properties);
    }

    @Test
    public void successfulClientSearchWithSomeData() {
        String guid = UUID.randomUUID().toString();

        ClientGeneralInfo client = new ClientGeneralInfo();
        client.setAccountList(Arrays.asList(new ClientAccountInfo(), new ClientAccountInfo()));
        client.setGuid(guid);

        Mockito
                .when(restTemplate.getForEntity(
                        any(),
                        any()
                ))
                .thenReturn(ResponseEntity.ok(client));

        ClientGeneralInfo test = service.getClientInfoByGUID(guid);

        Assertions.assertNotNull(test);
        Assertions.assertNotNull(test.getAccountList());
        Assertions.assertEquals(guid, test.getGuid());
    }

    @Test
    public void notFoundClient() {
        Mockito
                .when(restTemplate.getForEntity(
                        any(),
                        any()
                ))
                .thenReturn(ResponseEntity.ok(null));

        ClientException clientException = Assertions.assertThrows(
                ClientException.class,
                () -> service.getClientInfoByGUID("123")
        );

        Assertions.assertEquals(ErrorCode.PROFILE_NOT_FOUND, clientException.getErrorCode());
    }

    @Test
    public void badRequestStatusCode() {
        ClientGeneralInfo client = new ClientGeneralInfo();

        Mockito
                .when(restTemplate.getForEntity(
                        any(),
                        any()
                ))
                .thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(client));

        ClientException clientException = Assertions.assertThrows(
                ClientException.class,
                () -> service.getClientInfoByGUID("123")
        );

        Assertions.assertEquals(ErrorCode.PROFILE_NOT_FOUND, clientException.getErrorCode());
    }

    @Test
    public void nullAccountList() {
        ClientGeneralInfo client = new ClientGeneralInfo();

        Mockito
                .when(restTemplate.getForEntity(
                        any(),
                        any()
                ))
                .thenReturn(ResponseEntity.ok(client));

        ClientException clientException = Assertions.assertThrows(
                ClientException.class,
                () -> service.getClientInfoByGUID("123")
        );

        Assertions.assertEquals(ErrorCode.ACCOUNT_NOT_FOUND, clientException.getErrorCode());
    }

    @Test
    public void emptyAccountList() {
        ClientGeneralInfo client = new ClientGeneralInfo();
        client.setAccountList(Collections.emptyList());

        Mockito
                .when(restTemplate.getForEntity(
                        any(),
                        any()
                ))
                .thenReturn(ResponseEntity.ok(client));

        ClientException clientException = Assertions.assertThrows(
                ClientException.class,
                () -> service.getClientInfoByGUID("123")
        );

        Assertions.assertEquals(ErrorCode.ACCOUNT_NOT_FOUND, clientException.getErrorCode());
    }

}
