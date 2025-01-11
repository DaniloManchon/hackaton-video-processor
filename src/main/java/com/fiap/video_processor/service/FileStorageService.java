package com.fiap.video_processor.service;

import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.zeroturnaround.zip.ZipUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Log4j2
@Service
public class FileStorageService {

    @Value("${path.storage}")
    private String STORAGE_DIR;

    public void createDirectory(String videoName) {
        try {
            File dir = new File(STORAGE_DIR + File.separator + videoName);
            if (!dir.exists()) {
                dir.mkdirs();
                log.info("New directory created: {}", videoName);
            } else {
                log.warn("Directory {} already exists", videoName);
            }
        } catch (Exception e) {
            log.error(e);
        }
    }

    public void saveFileInDirectory(String videoName, String imageName, BufferedImage bufferedImage, String imageType) {
        String path = STORAGE_DIR + File.separator + videoName;
        String fullPath = path + File.separator + imageName;
        try {
            ImageIO.write(bufferedImage, imageType, new File(fullPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void zipDirectory(String videoName) {
        String root = STORAGE_DIR + File.separator + videoName;
        String zip = root + ".zip";
        try {
            log.info("Zipping directory {}", videoName);
            ZipUtil.pack(new File(root), new File(zip));
            log.info("Zip {} created", videoName);
        } catch (Exception e) {
            log.error(e);
        }
    }

    public void deleteDirectory(String videoName) {
        File dir = new File(STORAGE_DIR + File.separator + videoName);
        try {
            if (dir.exists()) {
                FileUtils.deleteDirectory(dir);
                log.info("Directory deleted: {}", videoName);
            } else {
                log.warn("directory {} not found", videoName);
            }
        } catch (IOException e) {
            log.error(e);
        }
    }

}
