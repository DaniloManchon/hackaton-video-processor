package com.fiap.video_processor.controller;

import com.fiap.video_processor.service.FileStorageService;
import com.fiap.video_processor.service.VideoManipulationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Log4j2
@RestController
public class Controller {

    @Autowired
    private VideoManipulationService videoManipulationService;

    @Autowired
    FileStorageService fileStorageService;

    @PostMapping("/upload")
    public boolean uploadFile(@RequestParam(value="file") MultipartFile file) throws Exception {
        videoManipulationService.convertVideoToImages(file);
        return true;
    }

}
