package com.odp.opendataplatform.spark.queue;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class SparkQueueConsumerListener {
    private final StringRedisTemplate redisTemplate;

    public SparkQueueConsumerListener(
            @Qualifier("stringRedisTemplate") StringRedisTemplate redisTemplate,
            MessageListener sparkJobMessageListener) {
        this.redisTemplate = redisTemplate;
        subscribeToSparkJobs(sparkJobMessageListener);
    }

    private void subscribeToSparkJobs(MessageListener sparkJobMessageListener) {
        // Subscribe to the "spark-job-channel"
        redisTemplate.getConnectionFactory().getConnection().subscribe(
                sparkJobMessageListener,
                "spark-job-queue".getBytes()
        );
    }
}