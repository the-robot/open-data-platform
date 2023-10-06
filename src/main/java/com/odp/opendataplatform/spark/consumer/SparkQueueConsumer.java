package com.odp.opendataplatform.spark.consumer;

import com.odp.opendataplatform.queue.service.SparkQueueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SparkQueueConsumer {
    private static final Logger logger = LoggerFactory.getLogger(SparkQueueConsumer.class);

    private final SparkQueueService sparkQueueService;
    private final TaskExecutor taskExecutor;

    @Autowired
    public SparkQueueConsumer(
            @Qualifier("sparkQueueService") SparkQueueService sparkQueueService,
            @Qualifier("applicationTaskExecutor") TaskExecutor taskExecutor) {
        this.sparkQueueService = sparkQueueService;
        this.taskExecutor = taskExecutor;
    }

    @Scheduled(fixedDelay = 5000)
    public void consumeSparkJobs() {
        String message = sparkQueueService.dequeue("spark-job-queue");
        if (message != null) {
            // Trigger the appropriate Spark job based on the message content
            logger.info("Received Spark job message: {}", message);

            // Trigger the appropriate Spark job based on the message content
            Runnable sparkJob = () -> {
                // Implement logic to start the Spark job here
                // This code will run in a separate thread managed by the TaskExecutor
                // ...
            };

            // Submit the Spark job for execution
            taskExecutor.execute(sparkJob);
        }
    }
}
