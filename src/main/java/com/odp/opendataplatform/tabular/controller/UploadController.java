package com.odp.opendataplatform.tabular.controller;

import com.odp.opendataplatform.tabular.dto.UploadResult;
import com.odp.opendataplatform.tabular.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/tabular")
public class UploadController {
    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

    private final UploadService uploadService;

    @Autowired
    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping("/upload")
    public String upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("fileName") String desiredFileName
    ) {
        logger.info("Received a file upload request");

        // Handle the result or return a response to the client
        UploadResult result = uploadService.saveUploadedFile(file, desiredFileName);
        return result.getFilename();
    }
}
