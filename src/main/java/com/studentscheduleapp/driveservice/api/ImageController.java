package com.studentscheduleapp.driveservice.api;

import com.studentscheduleapp.driveservice.services.FileService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class ImageController {

    @Autowired
    private FileService fileService;

    @PostMapping("upload")
    public ResponseEntity<String> upload(@RequestBody Byte[] file) {
        if (file == null || file.length == 0) {
            Logger.getGlobal().info("bad request: file is null or empty");
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

    @GetMapping("{name}")
    public ResponseEntity<Byte[]> download(@PathVariable("name") String name){
        File f = null;
        try {
            f = fileService.get(name);
        } catch (IOException e) {
            Logger.getGlobal().info("download failed:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        if(f != null){
            try {
                byte[] bs = FileUtils.readFileToByteArray(f);
                Byte[] Bs = new Byte[bs.length];
                for (int i = 0; i < bs.length; i++){
                    Bs[i] = bs[i];
                }
                Logger.getGlobal().info("file " + name + " send successful");
                return ResponseEntity.ok(Bs);
            } catch (IOException e) {
                Logger.getGlobal().info("not found: file " + name + " not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
        Logger.getGlobal().info("not found: file " + name + " not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("{name}")
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