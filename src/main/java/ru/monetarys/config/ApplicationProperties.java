package ru.monetarys.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import ru.monetarys.domain.exception.ErrorDefinition;

import java.util.List;
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

        private List<String> availableAccountCurrencies;
        private List<String> availablePayerAccountStatuses;
        private List<String> availablePayerAccountTypes;
        private List<String> availablePayeeAccountStatuses;
        private List<String> availablePayeeAccountTypes;
        private List<String> unavailableAccountsRegex;
        private String phoneRegex;
        private String availableCitizenshipCountryCode;
        private String operationCode;
        private String operationCurrency;
        private String transferDescription;
    }

}
