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
import ru.monetarys.exceptions.ClientErrorCode;
import ru.monetarys.exceptions.ClientException;
import ru.monetarys.config.ApplicationProperties;
import ru.monetarys.domain.integration.ClientGeneralInfo;
import ru.monetarys.integration.mapper.ClientProfileRsMapper;
import ru.monetarys.integration.mapper.ClientProfileRsMapperImpl;
import ru.monetarys.rs.integration.ClientGeneralInfoRs;
import ru.monetarys.integration.service.impl.ClientProfileServiceImpl;

import java.net.URI;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static ru.monetarys.rest.ClientProfileServiceImplUtil.*;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
class ClientProfileServiceImplTest {

    @Spy
    @InjectMocks
    private ClientProfileServiceImpl service;

    @Mock
    private RestTemplate restTemplate;

    private final ApplicationProperties properties = getApplicationProperties();;
    private final ClientProfileRsMapper rsMapper = new ClientProfileRsMapperImpl();;

    @BeforeEach
    public void setup() {
        service = new ClientProfileServiceImpl(restTemplate, properties, rsMapper);
    }

    @Test
    public void successfulClientSearchWithSomeData() {
        Mockito.when(restTemplate.getForEntity(any(), any()))
                .thenReturn(ResponseEntity.ok(getClientGeneralInfoWithAccountList()));

        ClientGeneralInfo test = service.getClientInfoByGuid(GUID);

        Mockito.verify(restTemplate, Mockito.times(1)).getForEntity(eq(URI.create(URL)), eq(ClientGeneralInfoRs.class));


        Assertions.assertNotNull(test);
        Assertions.assertNotNull(test.getAccountList());
        Assertions.assertEquals(GUID, test.getGuid());
        Assertions.assertEquals(FIRST_NAME, test.getPersonalInfo().getFirstName());
        Assertions.assertEquals(LAST_NAME, test.getPersonalInfo().getLastName());
        Assertions.assertNotNull(test.getContacts().getPhoneNumber());
        Assertions.assertNotNull(test.getPersonalInfo());
    }

    @Test
    public void notFoundClient() {
        Mockito.when(restTemplate.getForEntity(any(), any()))
                .thenReturn(ResponseEntity.ok(null));


        ClientException clientException = Assertions.assertThrows(
                ClientException.class,
                () -> service.getClientInfoByGuid(GUID)
        );

        Mockito.verify(restTemplate, Mockito.times(1)).getForEntity(any(), any());

        Assertions.assertEquals(ClientErrorCode.PROFILE_NOT_FOUND, clientException.getClientErrorCode());
    }

    @Test
    public void badRequestStatusCode() {
        Mockito.when(restTemplate.getForEntity(any(), any()))
                .thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getEmptyClientGeneralInfo()));

        ClientException clientException = Assertions.assertThrows(
                ClientException.class,
                () -> service.getClientInfoByGuid(GUID)
        );

        Mockito.verify(restTemplate, Mockito.times(1)).getForEntity(any(), any());

        Assertions.assertEquals(ClientErrorCode.PROFILE_NOT_FOUND, clientException.getClientErrorCode());
    }

    @Test
    public void nullAccountList() {
        Mockito.when(restTemplate.getForEntity(any(), any()))
                .thenReturn(ResponseEntity.ok(getEmptyClientGeneralInfo()));


        ClientException clientException = Assertions.assertThrows(
                ClientException.class,
                () -> service.getClientInfoByGuid(GUID)
        );

        Mockito.verify(restTemplate, Mockito.times(1)).getForEntity(any(), any());

        Assertions.assertEquals(ClientErrorCode.ACCOUNT_NOT_FOUND, clientException.getClientErrorCode());
    }

    @Test
    public void emptyAccountList() {
        Mockito.when(restTemplate.getForEntity(any(), any()))
                .thenReturn(ResponseEntity.ok(getClientGeneralInfoWithEmptyAccountList()));


        ClientException clientException = Assertions.assertThrows(
                ClientException.class,
                () -> service.getClientInfoByGuid(GUID)
        );

        Mockito.verify(restTemplate, Mockito.times(1)).getForEntity(any(), any());

        Assertions.assertEquals(ClientErrorCode.ACCOUNT_NOT_FOUND, clientException.getClientErrorCode());
    }

}
