package com.odp.opendataplatform.file.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {
    @Value("${uploadDir}")
    private String uploadDir;

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    public Path saveFile(MultipartFile file) throws IOException {
        // Create the directory if it doesn't exist
        Path directoryPath = Path.of(uploadDir);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        // Prefix the desired file name with a Unix timestamp
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        // Save the uploaded file to the specified directory with the prefixed filename
        Path filePath = directoryPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return filePath;
    }

    public String getFileName(Path filePath) {
        return filePath.getFileName().toString();
    }

    public byte[] readFile(String filePath) throws IOException {
        return Files.readAllBytes(Path.of(filePath));
    }
}
