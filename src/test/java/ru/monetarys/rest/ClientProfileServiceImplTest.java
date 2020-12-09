package ru.monetarys.rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import ru.monetarys.dto.ClientGeneralInfo;
import ru.monetarys.exceptions.ClientException;
import ru.monetarys.exceptions.ErrorCode;
import ru.monetarys.services.clientprofile.ApplicationProperties;
import ru.monetarys.services.clientprofile.ClientProfileServiceImpl;

import static org.mockito.ArgumentMatchers.any;
import static ru.monetarys.rest.ClientProfileServiceImplUtil.*;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
class ClientProfileServiceImplTest {

    @Spy
    @InjectMocks
    private ClientProfileServiceImpl service;

    @Mock
    private RestTemplate restTemplate;

    private final ApplicationProperties properties = getApplicationProperties();

    @BeforeEach
    public void setup() {
        service = new ClientProfileServiceImpl(restTemplate, properties);
    }

    @Test
    public void successfulClientSearchWithSomeData() {
        Mockito.when(restTemplate.getForEntity(any(), any()))
                .thenReturn(ResponseEntity.ok(getClientGeneralInfoWithAccountList()));

        ClientGeneralInfo test = service.getClientInfoByGUID(GUID);

        Assertions.assertNotNull(test);
        Assertions.assertNotNull(test.getAccountList());
        Assertions.assertEquals(GUID, test.getGuid());
    }

    @Test
    public void notFoundClient() {
        Mockito.when(restTemplate.getForEntity(any(), any()))
                .thenReturn(ResponseEntity.ok(null));

        ClientException clientException = Assertions.assertThrows(
                ClientException.class,
                () -> service.getClientInfoByGUID("123")
        );

        Assertions.assertEquals(ErrorCode.PROFILE_NOT_FOUND, clientException.getErrorCode());
    }

    @Test
    public void badRequestStatusCode() {
        Mockito.when(restTemplate.getForEntity(any(), any()))
                .thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getEmptyClientGeneralInfo()));

        ClientException clientException = Assertions.assertThrows(
                ClientException.class,
                () -> service.getClientInfoByGUID("123")
        );

        Assertions.assertEquals(ErrorCode.PROFILE_NOT_FOUND, clientException.getErrorCode());
    }

    @Test
    public void nullAccountList() {
        Mockito.when(restTemplate.getForEntity(any(), any()))
                .thenReturn(ResponseEntity.ok(getEmptyClientGeneralInfo()));

        ClientException clientException = Assertions.assertThrows(
                ClientException.class,
                () -> service.getClientInfoByGUID("123")
        );

        Assertions.assertEquals(ErrorCode.ACCOUNT_NOT_FOUND, clientException.getErrorCode());
    }

    @Test
    public void emptyAccountList() {
        Mockito.when(restTemplate.getForEntity(any(), any()))
                .thenReturn(ResponseEntity.ok(getClientGeneralInfoWithEmptyAccountList()));

        ClientException clientException = Assertions.assertThrows(
                ClientException.class,
                () -> service.getClientInfoByGUID("123")
        );

        Assertions.assertEquals(ErrorCode.ACCOUNT_NOT_FOUND, clientException.getErrorCode());
    }

}
