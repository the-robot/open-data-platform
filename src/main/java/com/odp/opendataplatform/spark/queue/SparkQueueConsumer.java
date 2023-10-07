package com.odp.opendataplatform.spark.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import com.odp.opendataplatform.spark.job.SparkJob; // Import SparkJob

@Configuration
public class SparkQueueConsumer {
    private static final Logger logger = LoggerFactory.getLogger(SparkQueueConsumer.class);
    private final SparkJob sparkJob;

    @Autowired
    public SparkQueueConsumer(SparkJob sparkJob) {
        this.sparkJob = sparkJob;
    }

    @Bean
    public MessageListener sparkJobMessageListener() {
        return (message, pattern) -> {
            String channel = new String(message.getChannel());
            String content = new String(message.getBody());

            // Handle the received message (content) based on the channel
            logger.info("Received spark job message to queue on channel {}: {}", channel, content);

            // Extract the file name from the message content (modify as needed)
            String fileName = extractFileName(content);

            // Call the SparkJob with the extracted file name
            sparkJob.run(fileName);
        };
    }

    // Implement a method to extract the file name from the message content
    private String extractFileName(String messageContent) {
        // Implement your logic to extract the file name from the message content
        // For example, if the messageContent contains the file name, extract it here.
        return messageContent;
    }
}
