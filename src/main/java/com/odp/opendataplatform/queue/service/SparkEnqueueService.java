package com.odp.opendataplatform.queue.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Qualifier("uploadQueueService")
public class SparkEnqueueService implements QueueService {
    private final StringRedisTemplate stringRedisTemplate;

    public SparkEnqueueService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void enqueue(String queueName, String message) {
        // Enqueue the message into the "upload" queue
        stringRedisTemplate.opsForList().rightPush(queueName, message);
    }

    @Override
    public String dequeue(String queueName) {
        // Dequeue a message from the "upload" queue
        return stringRedisTemplate.opsForList().leftPop(queueName);
    }
}
