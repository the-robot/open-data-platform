package com.odp.opendataplatform.spark.consumer;

import com.odp.opendataplatform.queue.service.SparkQueueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SparkQueueConsumer {
    private static final Logger logger = LoggerFactory.getLogger(SparkQueueConsumer.class);

    private final SparkQueueService sparkQueueService;

    @Autowired
    public SparkQueueConsumer(@Qualifier("sparkQueueService") SparkQueueService sparkQueueService) {
        this.sparkQueueService = sparkQueueService;
    }

    @Scheduled(fixedDelay = 10000)
    public void consumeSparkJobs() {
        String message = sparkQueueService.dequeue("spark-job-queue");
        if (message != null) {
            // Trigger the appropriate Spark job based on the message content
            logger.info("Received Spark job message: {}", message);

            // Implement logic to start the Spark job
            // ...
        }
    }
}
