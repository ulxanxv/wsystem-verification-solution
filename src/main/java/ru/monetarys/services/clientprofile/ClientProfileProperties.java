package ru.monetarys.services.clientprofile;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "application")
public class ClientProfileProperties {

    private ClientProfileService    clientProfileService;
    private ServiceProperties       serviceProperties;

    @Data
    public static class ClientProfileService {

        private String host;
        private String clientInfoByGuidPath;
        private String secret;
        private String authHeaderName;

    }

    @Data
    public static class ServiceProperties {

        private int connectionTimeout;
        private int readTimeout;
        private int maxConnectionTotal;
        private int maxConnectionPerRoute;

    }

}
