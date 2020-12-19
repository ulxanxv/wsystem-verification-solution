package ru.monetarys.integration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import ru.monetarys.exceptions.domain.ErrorDefinition;

import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {

    private ClientProfileService clientProfileService;
    private ClientProfileProperties clientProfileProperties;
    private MqTransferProperties mqTransferProperties;
    private TransferMoney transferMoney;

    @Data
    public static class ClientProfileService {

        private Map<String, ErrorDefinition> errorMappings;

        private String httpHeader = HttpHeaders.AUTHORIZATION;
        private String clientInfoByGuidPath;
        private String secret;
        private String host;
    }

    @Data
    public static class ClientProfileProperties {

        private int connectionTimeout;
        private int readTimeout;
        private int maxConnectionTotal;
        private int maxConnectionPerRoute;
    }

    @Data
    public static class MqTransferProperties {

        private String inExchangeName;
        private String outExchangeName;
        private String queueName;
        private String routingKey;
    }

    @Data
    public static class TransferMoney {

        private Map<String, ErrorDefinition> validationErrors;

        private String[] availableAccountCurrencies;
        private String[] availablePayerAccountStatuses;
        private String[] availablePayerAccountTypes;
        private String[] availablePayeeAccountStatuses;
        private String[] availablePayeeAccountTypes;
        private String availableCitizenshipCountryCode;
        private String operationCode;
        private String operationCurrency;
        private String transferDescription;
        private String phoneNumberRegex;
    }

}
