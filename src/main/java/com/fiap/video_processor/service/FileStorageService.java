package com.fiap.video_processor.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class FileStorageService {

    @Value("${path.storage}")
    private String STORAGE_DIR;

    public void saveFile(MultipartFile file) throws IOException {
        if (file == null) {
            throw new NullPointerException("file is null");
        }
        FileOutputStream fileOutputStream = new FileOutputStream( STORAGE_DIR + File.separator + file.getOriginalFilename());
        fileOutputStream.write(file.getBytes());
        fileOutputStream.close();

    }
}
