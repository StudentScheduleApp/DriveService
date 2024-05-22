package com.studentscheduleapp.driveservice.repos;

import org.springframework.web.multipart.MultipartFile;

public interface DriveRepo {
    String create(MultipartFile fileContent) throws Exception;
    public void delete(String name) throws Exception;
}
