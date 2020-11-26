package ru.monetarys.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class ServiceProperties {

    private int connectionTimeout = 10000;
    private int readTimeout = 30000;
    private int maxConnectionTotal = 1000;
    private int maxConnectionPerRoute = 10;

}
