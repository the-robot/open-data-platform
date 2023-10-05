package com.odp.opendataplatform.controller.tabular;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/tabular")
public class UploadController {
    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        logger.info("Received a file upload request");

        // Handle tabular data upload logic here
        return "Tabular data uploaded successfully!";
    }
}
