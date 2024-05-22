package com.studentscheduleapp.driveservice.http;

import com.studentscheduleapp.driveservice.properties.GlobalProperties;
import com.studentscheduleapp.driveservice.properties.YandexDriveProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class HeaderRequestInterceptor implements ClientHttpRequestInterceptor {


    @Autowired
    private GlobalProperties globalProperties;
    @Autowired
    private YandexDriveProperties yandexDriveProperties;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        request.getHeaders().set(globalProperties.getServiceTokenHeader(), globalProperties.getServiceToken());
        request.getHeaders().set(yandexDriveProperties.getAuthHeader(), yandexDriveProperties.getAuthToken());
        return execution.execute(request, body);
    }

}