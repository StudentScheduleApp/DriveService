package com.studentscheduleapp.driveservice.api;

import com.studentscheduleapp.driveservice.services.FileService;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

@RestController
@RequiredArgsConstructor
public class DriveController {

    private static final Logger log = LogManager.getLogger(DriveController.class);
    @Autowired
    private FileService fileService;

    @PostMapping("${mapping.upload}")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            log.warn("bad request: image is null or empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        String name;
        try {
            name = fileService.create(file);
        } catch (NullPointerException e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.warn("bad request: " + errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error("upload failed:" + errors);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        log.info("file " + name + " saved");
        return ResponseEntity.ok(name);
    }

    @DeleteMapping("${mapping.delete}/{name}")
    public ResponseEntity<Void> delete(@PathVariable("name") String name) {
        try {
            fileService.delete(name);
        } catch (IOException e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error("delete failed:" + errors);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        log.info("file " + name + " deleted");
        return ResponseEntity.ok().build();
    }

}