package com.odp.opendataplatform.spark.queue;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class SparkQueuePublisher {
    private final StringRedisTemplate redisTemplate;
    private final String redisChannel = "spark-job-queue"; // Specify the Redis channel name

    public SparkQueuePublisher(
            @Qualifier("stringRedisTemplate") StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void enqueue(String message) {
        // Publish the message to the Redis channel
        redisTemplate.convertAndSend(redisChannel, message);
    }
}
