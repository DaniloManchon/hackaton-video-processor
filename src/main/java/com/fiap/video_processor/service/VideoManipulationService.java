package com.fiap.video_processor.service;

import lombok.extern.log4j.Log4j2;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;

@Log4j2
@Service
public class VideoManipulationService {

    @Autowired
    FileStorageService fileStorageService;

    public void convertVideoToImages(MultipartFile upload) throws IOException {
        String videoName = upload.getOriginalFilename().replace(" ", "_");

        String imageType = "jpg";

        Java2DFrameConverter converter = new Java2DFrameConverter();
        FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(upload.getInputStream());

        frameGrabber.start();
        Frame frame;

        double frameRate = frameGrabber.getFrameRate();
        log.info("Video has {} frames and has frame rate of {}", frameGrabber.getLengthInFrames(), frameRate);

        fileStorageService.createDirectory(videoName);

        try {
            for (int currentTime = 0; currentTime <= frameGrabber.getLengthInFrames(); currentTime += 20) {
                frameGrabber.setFrameNumber(currentTime * (int) frameRate);
                frame = frameGrabber.grabFrame(false, true, true, false);

                if (frame == null)
                    continue;

                BufferedImage bufferedImage = converter.convert(frame);
                String imageName = "frame_at" + currentTime + "." + imageType;
                fileStorageService.saveFileInDirectory(videoName, imageName, bufferedImage, imageType);

            }
            frameGrabber.stop();
            log.info("Conversion complete");

            fileStorageService.zipDirectory(videoName);

            log.info("Deleting old directory");
            fileStorageService.deleteDirectory(videoName);

        } catch (Exception e) {
            log.error(e);
        }
    }
}
