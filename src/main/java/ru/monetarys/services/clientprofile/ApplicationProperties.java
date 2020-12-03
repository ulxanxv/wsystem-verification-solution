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

}
