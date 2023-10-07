package com.odp.opendataplatform.tabular.service;

import com.odp.opendataplatform.file.service.FileService;
import com.odp.opendataplatform.spark.queue.SparkQueuePublisher;
import com.odp.opendataplatform.tabular.dto.UploadResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;

@Service
public class UploadService {
    private static final Logger logger = LoggerFactory.getLogger(UploadService.class);
    private final FileService fileService;
    private final SparkQueuePublisher sparkQueuePublisher;

    public UploadService(FileService fileService, SparkQueuePublisher sparkQueuePublisher) {
        this.fileService = fileService;
        this.sparkQueuePublisher = sparkQueuePublisher;
    }

    public UploadResult saveUploadedFile(MultipartFile file) {
        if (file.isEmpty()) {
            return new UploadResult(false, "Please select a file to upload.");
        }

        try {
            // Save the uploaded file to HDFS using the FileService
            String hdfsFilePath = fileService.saveFileToHDFS(file);

            logger.info("File uploaded and saved to HDFS: {}", hdfsFilePath);

            // Enqueue a message with the HDFS file path using the SparkQueuePublisher
            sparkQueuePublisher.enqueue(hdfsFilePath);

            return new UploadResult(true, hdfsFilePath);
        } catch (IOException e) {
            logger.error("Error uploading file: {}", e.getMessage());
            return new UploadResult(false, "Error uploading file.");
        }
    }
}
