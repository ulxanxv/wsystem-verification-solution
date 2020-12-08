package ru.monetarys;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import ru.monetarys.dto.ClientAccountInfo;
import ru.monetarys.dto.ClientGeneralInfo;
import ru.monetarys.services.clientprofile.ApplicationProperties;
import ru.monetarys.services.clientprofile.ClientProfileServiceImpl;

import java.util.Arrays;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;


@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class MonetarysApplicationTests {

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
	public void successfulСustomerSearchWithSomeData() {
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

}
