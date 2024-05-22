package com.studentscheduleapp.driveservice.services;

import org.springframework.stereotype.Service;

@Service
public class UrlService {

    public String getNameFromImageUrl(String url) {
        try {
            return url.split("/")[url.split("/").length-1];//url.substring(43, url.length() - 16);
        } catch (Exception e) {
            return "";
        }
    }

}
