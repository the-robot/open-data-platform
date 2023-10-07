package com.odp.opendataplatform.spark.job;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Component
public class SparkJob {
    private static final Logger logger = LoggerFactory.getLogger(SparkJob.class);

    private final String sparkMaster;
    private final String appName;
    private final String jobFilesPrefix;

    public SparkJob(
            @Value("${spark.master}") String sparkMaster,
            @Value("${spark.app.name}") String appName,
            @Value("${spark.job.files}") String jobFilesPrefix) {
        this.sparkMaster = sparkMaster;
        this.appName = appName;
        this.jobFilesPrefix = jobFilesPrefix;
    }

    public void run(String fileName) {
        SparkSession sparkSession = null;

        try {
            // Create SparkSession
            sparkSession = SparkSession.builder()
                    .appName(appName)
                    .master(sparkMaster)
                    .getOrCreate();

            logger.info("Starting Spark job...");
            logger.info("Job at {}", jobFilesPrefix + "/" + fileName);

            // Read the CSV file using Spark
            Dataset<Row> data = sparkSession.read()
                    .option("header", "true") // If the first row is a header
                    .csv( jobFilesPrefix + "/" + fileName);

            // Show the content
            data.show();

            logger.info("Spark job completed successfully.");
        } catch (Exception e) {
            logger.error("Error executing Spark job: {}", e.getMessage(), e);
        } finally {
            // Close the Spark session when the job is done or if an exception occurs
            if (sparkSession != null) {
                sparkSession.close();
            }
        }
    }
}
