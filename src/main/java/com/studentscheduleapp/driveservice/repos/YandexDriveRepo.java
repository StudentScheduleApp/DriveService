package com.studentscheduleapp.driveservice.repos;

import com.google.api.client.json.Json;
import com.studentscheduleapp.driveservice.properties.YandexDriveProperties;
import org.apache.commons.io.IOUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Repository
public class YandexDriveRepo implements DriveRepo{
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private YandexDriveProperties yandexDriveProperties;

    @Override
    public String create(MultipartFile fileContent) throws Exception {
        String name = String.valueOf(System.currentTimeMillis());
        ResponseEntity<HashMap> r1 = restTemplate.getForEntity(yandexDriveProperties.getUploadUrl() + name, HashMap.class);
        if (r1.getStatusCode().is2xxSuccessful() && r1.getBody() != null){
            String upload = (String) r1.getBody().get("href");
            if(upload != null && !upload.isEmpty()){
                InputStream inputStream = fileContent.getInputStream();
                byte[] data = IOUtils.toByteArray(inputStream);
                HttpEntity<byte[]> requestEntity = new HttpEntity<>(data);
                ResponseEntity<Void> r2 = restTemplate.exchange(upload, HttpMethod.PUT, requestEntity, Void.class);
                if(r2.getStatusCode().is2xxSuccessful()){
                    return yandexDriveProperties.getDownloadUrl() + name;
                }
                throw new Exception("request to " + upload + " return code " + r2.getStatusCode());
            }
            throw new Exception("upload url is null or empty");
        }
        throw new Exception("request to " + yandexDriveProperties.getUploadUrl() + name + " return code " + r1.getStatusCode());
    }

    @Override
    public void delete(String name) throws Exception {
        ResponseEntity<Void> r = restTemplate.exchange(yandexDriveProperties.getDeleteUrl() + name, HttpMethod.DELETE, null, Void.class);
        if(!r.getStatusCode().is2xxSuccessful())
            throw new Exception("request to " + yandexDriveProperties.getDeleteUrl() + name + " return code " + r.getStatusCode());
    }
}
