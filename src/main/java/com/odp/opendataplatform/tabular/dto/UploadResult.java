package com.odp.opendataplatform.tabular.dto;

public class UploadResult {
    private final boolean success;
    private final String message;
    private String filename;

    public UploadResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public UploadResult(boolean success, String message, String filename) {
        this.success = success;
        this.message = message;
        this.filename = filename;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getFilename() {
        return filename;
    }
}