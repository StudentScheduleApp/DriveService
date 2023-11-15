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

@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class ImageController {

    @Autowired
    private FileService fileService;

    @PostMapping("upload")
    public ResponseEntity<String> upload(@RequestParam("image") MultipartFile file) {
        if (file == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        String name = "";
        try {
            name = fileService.create(file);
        } catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(name);
    }

    @GetMapping("{name}")
    public ResponseEntity<Byte[]> download(@PathVariable("name") String name){
        File f = null;
        try {
            f = fileService.get(name);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        if(f != null){
            try {
                byte[] bs = FileUtils.readFileToByteArray(f);
                Byte[] Bs = new Byte[bs.length];
                for (int i = 0; i < bs.length; i++){
                    Bs[i] = bs[i];
                }
                return ResponseEntity.ok(Bs);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("{name}")
    public ResponseEntity<Void> delete(@PathVariable("name") String name){
        try {
            fileService.delete(name);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok().build();
    }

}