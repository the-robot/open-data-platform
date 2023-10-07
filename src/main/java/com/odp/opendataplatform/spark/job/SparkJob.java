package com.odp.opendataplatform.spark.job;

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

    public SparkJob(
            @Value("${spark.master}") String sparkMaster,
            @Value("${spark.app.name}") String appName) {
        this.sparkMaster = sparkMaster;
        this.appName = appName;
    }

    public void run(String filePath) {
        SparkSession sparkSession = null;

        try {
            // Create SparkSession
            sparkSession = SparkSession.builder()
                    .appName(appName)
                    .master(sparkMaster)
                    .getOrCreate();

            logger.info("Starting Spark job...");

            // Your Spark job logic here
            logger.info(filePath);

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
