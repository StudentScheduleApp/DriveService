package com.studentscheduleapp.driveservice.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class GoogleDriveProperties {
    @Value("${google.path.tokens}")
    private String tokensPath;
    @Value("${google.app.name}")
    private String appName;

}
