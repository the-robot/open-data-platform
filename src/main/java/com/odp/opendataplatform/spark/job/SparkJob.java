package com.odp.opendataplatform.spark.job;

import com.odp.opendataplatform.spark.validator.CommonValidator;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SparkJob {
    private static final Logger logger = LoggerFactory.getLogger(SparkJob.class);

    private final String sparkMaster;
    private final Integer sparkPartitions;
    private final String appName;

    private final String hadoopUri;

    private final CommonValidator commonValidator = new CommonValidator();

    public SparkJob(
            @Value("${spark.master}") String sparkMaster,
            @Value("${spark.partitions}") Integer sparkPartitions,
            @Value("${spark.app.name}") String appName,
            @Value("${hdfs.master.spark.uri}") String hadoopUri) {
        this.sparkMaster = sparkMaster;
        this.sparkPartitions = sparkPartitions;
        this.appName = appName;
        this.hadoopUri = hadoopUri;
    }

    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@(.+)$";


    public void run(String filePath) {
        SparkSession sparkSession = null;

        try {
            // Create SparkSession
            sparkSession = SparkSession.builder()
                    .appName(appName)
                    .master(sparkMaster)
                    .getOrCreate();

            logger.info("Starting Spark job for file: {}", filePath);

            // Read the CSV file using Spark
            Dataset<Row> data = sparkSession.read()
                    .option("header", "true") // If the first row is a header
                    .option("numPartitions", this.sparkPartitions)
                    .csv( this.hadoopUri + filePath);


            // validate email
            Map<String, List<Integer>> errors = this.commonValidator.validateEmailColumn(data, "email");

            // Show the content
            data.show();

            logger.info("Error: {}", errors);

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
