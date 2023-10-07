package com.odp.opendataplatform.spark.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;

@Configuration
public class SparkQueueConsumer {
    private static final Logger logger = LoggerFactory.getLogger(SparkQueueConsumer.class);

    @Bean
    public MessageListener sparkJobMessageListener() {
        return (message, pattern) -> {
            String channel = new String(message.getChannel());
            String content = new String(message.getBody());

            // Handle the received message (content) based on the channel
            logger.info("Received Spark job message on channel {}: {}", channel, content);

            // Trigger the appropriate Spark job based on the message content
            // Implement logic to start the Spark job here
            // This code will run in the same thread as the Redis message listener
            // ...

            // Note: Be cautious about long-running tasks in the message listener
            // If your Spark job logic takes a long time, consider using async processing.
        };
    }
}
