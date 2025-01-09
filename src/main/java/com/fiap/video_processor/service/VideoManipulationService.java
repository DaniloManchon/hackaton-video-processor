package com.fiap.video_processor.service;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class VideoManipulationService {

    @Autowired
    FileStorageService fileStorageService;

    @Value("${path.storage}")
    private String STORAGE_DIR;
    @Value("${path.ffmpeg}")
    private String FFMPEG_DIR;

    public void convertVideoToImages(MultipartFile upload) throws IOException {
        FFmpeg ffmpeg = new FFmpeg(FFMPEG_DIR + "ffmpeg.exe");
        FFprobe ffprobe = new FFprobe(FFMPEG_DIR + "ffprobe.exe");

        System.out.println(ffprobe.probe(STORAGE_DIR + File.separator + upload.getOriginalFilename()));
    }
}
