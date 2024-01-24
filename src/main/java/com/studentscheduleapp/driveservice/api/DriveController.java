package com.studentscheduleapp.driveservice.api;

import com.studentscheduleapp.driveservice.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
public class DriveController {

    @Autowired
    private FileService fileService;

    @PostMapping("${mapping.upload}")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            Logger.getGlobal().info("bad request: image is null or empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        String name = "";
        try {
            name = fileService.create(file);
        } catch (NullPointerException e){
            Logger.getGlobal().info("bad request: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            Logger.getGlobal().info("upload failed:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        Logger.getGlobal().info("file " + name + " saved successful");
        return ResponseEntity.ok(name);
    }
    @DeleteMapping("${mapping.delete}/{name}")
    public ResponseEntity<Void> delete(@PathVariable("name") String name){
        try {
            fileService.delete(name);
        } catch (IOException e) {
            Logger.getGlobal().info("delete failed:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        Logger.getGlobal().info("file " + name + " deleted successful");
        return ResponseEntity.ok().build();
    }

}