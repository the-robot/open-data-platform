package com.odp.opendataplatform.spark.queue;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class SparkQueuePublisher {
    private final StringRedisTemplate redisTemplate;
    private final String redisChannel;

    public SparkQueuePublisher(
            @Qualifier("stringRedisTemplate") StringRedisTemplate redisTemplate,
            @Value("${redis.channel.spark.queue}") String sparkJobChannel) {
        this.redisTemplate = redisTemplate;
        this.redisChannel = sparkJobChannel;
    }

    public void enqueue(String message) {
        // Publish the message to the Redis channel
        redisTemplate.convertAndSend(redisChannel, message);
    }
}
