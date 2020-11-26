package ru.monetarys.services.clientprofile;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "client-profile")
@Getter
@Setter
public class ClientProfileApiProperties {

    private String host;
    private String clientInfoByGuidPath;
    private String secret;

}
