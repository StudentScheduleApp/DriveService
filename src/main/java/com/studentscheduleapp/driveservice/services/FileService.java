package com.studentscheduleapp.driveservice.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileService {
    public String create(MultipartFile file) throws IOException {
        return null;
    }

    public File get(String name) throws IOException {
        return null;
    }

    public boolean delete(String name) throws IOException {
        return false;
    }
}
