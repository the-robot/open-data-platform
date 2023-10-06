package com.odp.opendataplatform.spark.service;

import com.odp.opendataplatform.queue.service.QueueService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Qualifier("uploadQueueService")
public class SparkQueueService implements QueueService {
    private final StringRedisTemplate stringRedisTemplate;

    public SparkQueueService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void enqueue(String queueName, String message) {
        // Enqueue the message into the "spark" queue
        stringRedisTemplate.opsForList().rightPush(queueName, message);
    }

    @Override
    public String dequeue(String queueName) {
        // Dequeue a message from the "spark" queue
        return stringRedisTemplate.opsForList().leftPop(queueName);
    }
}
