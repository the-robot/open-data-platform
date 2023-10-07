package com.odp.opendataplatform.spark.queue;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class SparkQueueConsumerListener {
    private final StringRedisTemplate redisTemplate;
    private final String sparkJobChannel;

    public SparkQueueConsumerListener(
            @Qualifier("stringRedisTemplate") StringRedisTemplate redisTemplate,
            MessageListener sparkJobMessageListener,
            @Value("${redis.channel.spark.queue}") String sparkJobChannel) {
        this.redisTemplate = redisTemplate;
        this.sparkJobChannel = sparkJobChannel;
        subscribeToSparkJobs(sparkJobMessageListener);
    }

    private void subscribeToSparkJobs(MessageListener sparkJobMessageListener) {
        // Subscribe to the configured channel
        redisTemplate.getConnectionFactory().getConnection().subscribe(
                sparkJobMessageListener,
                sparkJobChannel.getBytes()
        );
    }
}
