package com.odp.opendataplatform.queue.service;

public interface QueueService {
    /**
     * Enqueue a message into the specified queue.
     *
     * @param queueName The name or identifier of the queue.
     * @param message   The message to enqueue.
     */
    void enqueue(String queueName, String message);

    /**
     * Dequeue a message from the specified queue.
     *
     * @param queueName The name or identifier of the queue.
     * @return The dequeued message, or null if the queue is empty.
     */
    String dequeue(String queueName);
}
