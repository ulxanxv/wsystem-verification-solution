package ru.monetarys.config;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfiguration {

    @Bean
    public HttpClient httpClient(ServiceProperties properties) {
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(properties.getConnectionTimeout())
                .setSocketTimeout(properties.getReadTimeout())
                .build();

        return HttpClientBuilder.create()
                .setDefaultRequestConfig(config)
                .setMaxConnTotal(properties.getMaxConnectionTotal())
                .setMaxConnPerRoute(properties.getMaxConnectionPerRoute())
                .build();
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory(HttpClient httpClient) {
        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
        return new RestTemplate(clientHttpRequestFactory);
    }

}
