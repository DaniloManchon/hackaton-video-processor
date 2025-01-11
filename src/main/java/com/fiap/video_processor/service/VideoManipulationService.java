package com.fiap.video_processor.service;

import lombok.extern.log4j.Log4j2;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Log4j2
@Service
public class VideoManipulationService {

    @Autowired
    FileStorageService fileStorageService;

    @Value("${path.storage}")
    private String STORAGE_DIR;

    public void convertVideoToImages(MultipartFile upload) throws IOException {
        String imagePath = STORAGE_DIR;
        String imgType = "jpg";

        Java2DFrameConverter converter = new Java2DFrameConverter();
        FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(upload.getInputStream());

        frameGrabber.start();
        Frame frame;

        double frameRate = frameGrabber.getFrameRate();
        log.info("Video has {} frames and has frame rate of {}", frameGrabber.getLengthInFrames(), frameRate);

        try {
            for (int currentTime = 0; currentTime <= frameGrabber.getLengthInFrames(); currentTime += 20) {
                frameGrabber.setFrameNumber(currentTime * (int) frameRate);
                frame = frameGrabber.grabFrame(false, true, true, false);

                if (frame == null)
                    return;

                BufferedImage bufferedImage = converter.convert(frame);
                String path = imagePath + File.separator + "frame_at" + currentTime + "." + imgType;
                ImageIO.write(bufferedImage, imgType, new File(path));
            }

            frameGrabber.stop();
            log.info("Conversion complete. Please find the images at {}", imagePath);

        } catch (Exception e) {
            log.error(e);
        }
    }
}
