package com.odp.opendataplatform.spark.consumer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class TaskExecutorConfig {
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // Adjust the number of concurrent threads as needed
        executor.setMaxPoolSize(10); // Maximum concurrent threads
        executor.setQueueCapacity(25); // Queue size for pending tasks
        executor.initialize();
        return executor;
    }
}
