package ru.monetarys.services.clientprofile;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

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

        private String httpHeader = HttpHeaders.AUTHORIZATION;

        private String host;
        private String clientInfoByGuidPath;
        private String secret;
        private String profileNotFoundMessage;
        private String accountNotFoundMessage;

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

        private String payerAccountNotValid;
        private String payeeGuidNotValid;
        private String payeeAccIdNotValid;
        private String payeeAccountNotValid;
        private String payeeAccountNotAllowed;
        private String payeeCardNumberNotValid;
        private String payeePhoneNotValid;
        private String amountNotValid;
        private String messageToPayeeNotValid;

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
