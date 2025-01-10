package com.fiap.video_processor.service;

import lombok.extern.log4j.Log4j2;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
@Log4j2
@Service
public class VideoManipulationService {

    @Autowired
    FileStorageService fileStorageService;

    @Value("${path.storage}")
    private String STORAGE_DIR;

    public void convertVideoToImages(MultipartFile upload) throws Exception {

        String mp4Path = STORAGE_DIR + File.separator + upload.getOriginalFilename();
        String imagePath = STORAGE_DIR;
        convertMovietoJPG(mp4Path, imagePath);
        log.info("Conversion complete. Please find the images at " + imagePath);

    }
    public static void convertMovietoJPG(String mp4Path, String imagePath) throws Exception {
        Java2DFrameConverter converter = new Java2DFrameConverter();
        FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(mp4Path);

        frameGrabber.start();
        Frame frame;
        String imgType = "jpg";

        double frameRate = frameGrabber.getFrameRate();
        log.info("Video has " + frameGrabber.getLengthInFrames() + " frames and has frame rate of " + frameRate);

        try {
            for(int currentTime=0;currentTime<=frameGrabber.getLengthInFrames();currentTime+=20) {
                frameGrabber.setFrameNumber(currentTime * (int) frameRate);
                frame = frameGrabber.grabFrame(false, true, true, false);

                if (frame == null)
                    return;
                if (!frame.type.equals(Frame.Type.VIDEO))
                    continue;

                BufferedImage bi = converter.convert(frame);
                String path = imagePath + File.separator + "frame_at" + currentTime + "." + imgType ;
                ImageIO.write(bi, imgType, new File(path));
            }

            frameGrabber.stop();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
