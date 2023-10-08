package com.odp.opendataplatform.hadoop.service;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

@Service
public class HadoopService {
    @Value("${hdfs.master.uri}")
    private String hdfsMasterUri; // HDFS master URI, e.g., "hdfs://odp-hadoop:9000"

    @Value("${hdfs.user}")
    private String hdfsUser;

    public String saveUploads(MultipartFile file) throws IOException {
        return this.saveToHDFS("uploads", file);
    }

    public String savePraquet(MultipartFile file) throws IOException {
        return this.saveToHDFS("parquet", file);
    }

    private String saveToHDFS(String directory, MultipartFile file) throws IOException {
        // Hadoop configuration
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", hdfsMasterUri);

        // Hadoop FileSystem
        FileSystem fs = FileSystem.get(URI.create(hdfsMasterUri), conf);

        // Prefix the desired file name with a Unix timestamp
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        // Path on HDFS where the file will be stored
        String hdfsFilePath = this.hdfsUser + "/" + directory + "/" + fileName;

        try (InputStream in = file.getInputStream(); OutputStream out = fs.create(new Path(hdfsFilePath))) {

            // Copy data from the uploaded file to HDFS
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return hdfsFilePath;
    }
}
