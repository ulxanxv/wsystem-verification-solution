package ru.monetarys.config;

import lombok.RequiredArgsConstructor;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import ru.monetarys.interceptors.HeaderRequestInterceptor;
import ru.monetarys.services.clientprofile.ClientProfileProperties;

@Configuration
public class RestClientConfiguration {

    @Autowired
    private ClientProfileProperties apiProperties;

    private HttpClient httpClient() {
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(apiProperties.getServiceProperties().getConnectionTimeout())
                .setSocketTimeout(apiProperties.getServiceProperties().getReadTimeout())
                .build();

        return HttpClientBuilder.create()
                .setDefaultRequestConfig(config)
                .setMaxConnTotal(apiProperties.getServiceProperties().getMaxConnectionTotal())
                .setMaxConnPerRoute(apiProperties.getServiceProperties().getMaxConnectionPerRoute())
                .build();
    }

    private ClientHttpRequestFactory clientHttpRequestFactory(HttpClient httpClient) {
        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory(httpClient()));

        restTemplate
                .getInterceptors()
                .add(new HeaderRequestInterceptor(apiProperties.getClientProfileService().getAuthHeaderName(), apiProperties.getClientProfileService().getSecret()));

        return restTemplate;
    }

}
