package ru.monetarys.interceptors;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

@AllArgsConstructor
@Data
public class HeaderRequestInterceptor implements ClientHttpRequestInterceptor {

    private String headerName;
    private String headerValue;

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        httpRequest.getHeaders().set(headerName, headerValue);
        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }

}
