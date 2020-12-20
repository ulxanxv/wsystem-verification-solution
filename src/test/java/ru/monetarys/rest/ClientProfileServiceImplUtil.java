package ru.monetarys.rest;

import lombok.experimental.UtilityClass;
import ru.monetarys.exceptions.domain.ErrorDefinition;
import ru.monetarys.integration.ApplicationProperties;
import ru.monetarys.integration.rs.ClientAccountInfoRs;
import ru.monetarys.integration.rs.ClientContactsInfoRs;
import ru.monetarys.integration.rs.ClientGeneralInfoRs;
import ru.monetarys.integration.rs.ClientPersonalInfoRs;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class ClientProfileServiceImplUtil {

    public String GUID = "4886d861-9b92-4028-82c0-56adcf098a52";
    public String PHONE_NUMBER = "+79991442211";
    public String FIRST_NAME = "Ivanov";
    public String LAST_NAME = "Ivan";
    public String MIDDLE_NAME = "Ivanovich";
    public String COUNTRY_CODE = "+7";

    public String URL = "http://clientprofile.internal.anybank.ru/v1/clientInfoByGUID?clientGUID=" + GUID;

    public ApplicationProperties getApplicationProperties() {
        ApplicationProperties properties = new ApplicationProperties();

        properties.setMqTransferProperties(new ApplicationProperties.MqTransferProperties());
        properties.setClientProfileProperties(new ApplicationProperties.ClientProfileProperties());
        properties.setClientProfileService(new ApplicationProperties.ClientProfileService());
        /*
         * MqTransferProperties
         * */
        properties.getMqTransferProperties().setQueueName("bank_transfers.out.monetary");
        properties.getMqTransferProperties().setInExchangeName("bank_transfers.in");
        properties.getMqTransferProperties().setOutExchangeName("bank_transfers.out");
        properties.getMqTransferProperties().setRoutingKey("monetarys.rk");

        /*
         * ClientProfileService
         * */
        properties.getClientProfileService().setClientInfoByGuidPath("/v1/clientInfoByGUID");
        properties.getClientProfileService().setHost("http://clientprofile.internal.anybank.ru");
        properties.getClientProfileService().setSecret("QWRtaW46YWRtaW4=");

        Map<String, ErrorDefinition> errorMappings = new HashMap<>();

        ErrorDefinition definition = new ErrorDefinition();

        definition.setAttributeName("clientGuid");
        definition.setCode("ProfileNotFound");
        definition.setMsg("Не найден профиль клиента");
        errorMappings.put("PROFILE_NOT_FOUND", definition);

        definition.setAttributeName("accountList");
        definition.setCode("AccountNotFound");
        definition.setMsg("Не найден ни один счёт клиента");
        errorMappings.put("ACCOUNT_NOT_FOUND", definition);

        properties.getClientProfileService().setErrorMappings(errorMappings);

        /*
         * ClientProfileProperties
         * */
        properties.getClientProfileProperties().setConnectionTimeout(10000);
        properties.getClientProfileProperties().setReadTimeout(30000);
        properties.getClientProfileProperties().setMaxConnectionTotal(1000);
        properties.getClientProfileProperties().setMaxConnectionPerRoute(10);

        return properties;
    }

    public ClientGeneralInfoRs getClientGeneralInfoWithAccountList() {
        ClientGeneralInfoRs client = new ClientGeneralInfoRs();
        client.setAccountList(Arrays.asList(new ClientAccountInfoRs(), new ClientAccountInfoRs()));
        client.setGuid(GUID);

        ClientContactsInfoRs contactsInfo = new ClientContactsInfoRs();
        ClientPersonalInfoRs personalInfo = new ClientPersonalInfoRs();

        contactsInfo.setPhoneNumber(PHONE_NUMBER);
        personalInfo.setFirstName(FIRST_NAME);
        personalInfo.setLastName(LAST_NAME);
        personalInfo.setMiddleName(MIDDLE_NAME);
        personalInfo.setCitizenshipCountryCode(COUNTRY_CODE);

        client.setContacts(contactsInfo);
        client.setPersonalInfo(personalInfo);

        return client;
    }

    public ClientGeneralInfoRs getClientGeneralInfoWithEmptyAccountList() {
        ClientGeneralInfoRs client = new ClientGeneralInfoRs();
        client.setAccountList(Collections.emptyList());
        return client;
    }

    public ClientGeneralInfoRs getEmptyClientGeneralInfo() {
        return new ClientGeneralInfoRs();
    }

}
