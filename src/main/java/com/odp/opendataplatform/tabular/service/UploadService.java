package com.odp.opendataplatform.tabular.service;

import com.odp.opendataplatform.tabular.dto.UploadResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class UploadService {
    private static final Logger logger = LoggerFactory.getLogger(UploadService.class);

    @Value("${uploadDir}")
    private String uploadDir;

    public UploadResult saveUploadedFile(MultipartFile file, String desiredFileName) {
        if (file.isEmpty()) {
            return new UploadResult(false, "Please select a file to upload.");
        }
        // check if filename is empty (only spaces or length 0)
        if (!StringUtils.hasLength(desiredFileName.trim())) {
            return new UploadResult(false, "Please enter a filename.");
        }

        try {
            // Create the directory if it doesn't exist
            Path directoryPath = Path.of(uploadDir);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            // Prefix the desired file name with a Unix timestamp
            String fileName = System.currentTimeMillis() + "_" + desiredFileName;

            // Save the uploaded file to the specified directory with the prefixed filename
            Path filePath = directoryPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            logger.info("File uploaded and saved to: {}", filePath.toString());

            return new UploadResult(true, fileName); // Return a custom result object
        } catch (IOException e) {
            logger.error("Error uploading file: {}", e.getMessage());
            return new UploadResult(false, "Error uploading file.");
        }
    }
}
