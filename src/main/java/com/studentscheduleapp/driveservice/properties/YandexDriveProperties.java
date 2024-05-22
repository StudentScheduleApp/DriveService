package com.studentscheduleapp.driveservice.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class YandexDriveProperties {
    @Value("${yandex.auth.token}")
    private String authToken;
    @Value("${yandex.auth.header}")
    private String authHeader;
    @Value("${yandex.uploadurl}")
    private String uploadUrl;
    @Value("${yandex.downloadurl}")
    private String downloadUrl;
    @Value("${yandex.deleteurl}")
    private String deleteUrl;

}
