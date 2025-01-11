package com.fiap.video_processor.controller;

import com.fiap.video_processor.service.VideoManipulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class Controller {

    @Autowired
    private VideoManipulationService videoManipulationService;

    @PostMapping("/upload")
    public boolean uploadFile(@RequestParam(value = "file") MultipartFile file) {
        try {
            videoManipulationService.convertVideoToImages(file);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
