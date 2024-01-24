package com.studentscheduleapp.driveservice.services;

import com.studentscheduleapp.driveservice.repos.GoogleDriveRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {
    @Autowired
    private GoogleDriveRepo googleDriveRepo;
    public String create(MultipartFile file) throws IOException {
        return googleDriveRepo.create(file);
    }

    public boolean delete(String name) throws IOException {
        return googleDriveRepo.delete(name);
    }
}
