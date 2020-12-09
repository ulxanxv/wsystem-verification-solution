package ru.monetarys.rest;

import lombok.experimental.UtilityClass;
import ru.monetarys.dto.ClientAccountInfo;
import ru.monetarys.dto.ClientGeneralInfo;
import ru.monetarys.services.clientprofile.ApplicationProperties;

import java.util.Arrays;
import java.util.Collections;

@UtilityClass
public class ClientProfileServiceImplUtil {

    public String GUID = "4886d861-9b92-4028-82c0-56adcf098a52";

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
        properties.getClientProfileService().setAccountNotFoundMessage("Не найден ни один счёт клиента");
        properties.getClientProfileService().setClientInfoByGuidPath("/v1/clientInfoByGUID");
        properties.getClientProfileService().setHost("http://clientprofile.internal.anybank.ru");
        properties.getClientProfileService().setProfileNotFoundMessage("Не найден профиль клиента");
        properties.getClientProfileService().setSecret("QWRtaW46YWRtaW4=");

        /*
         * ClientProfileProperties
         * */
        properties.getClientProfileProperties().setConnectionTimeout(10000);
        properties.getClientProfileProperties().setReadTimeout(30000);
        properties.getClientProfileProperties().setMaxConnectionTotal(1000);
        properties.getClientProfileProperties().setMaxConnectionPerRoute(10);

        return properties;
    }

    public ClientGeneralInfo getClientGeneralInfoWithAccountList() {
        ClientGeneralInfo client = new ClientGeneralInfo();
        client.setAccountList(Arrays.asList(new ClientAccountInfo(), new ClientAccountInfo()));
        client.setGuid(GUID);
        return client;
    }

    public ClientGeneralInfo getClientGeneralInfoWithEmptyAccountList() {
        ClientGeneralInfo client = new ClientGeneralInfo();
        client.setAccountList(Collections.emptyList());
        return client;
    }

    public ClientGeneralInfo getEmptyClientGeneralInfo() {
        return new ClientGeneralInfo();
    }

}
