package com.parser.orderparser.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Arun Singh
 * Configuration Class.
 * All Required Beans are Declared here.
 */

@Configuration
public class Configurations {

    @Value("${executor.pool.size.core}")
    private Integer corePoolSize;

    @Value("${executor.pool.size.max}")
    private Integer maxPoolSize;

    @Value("${executor.queue.capacity}")
    private Integer queueCapacity;

    @Value("${executor.thread.keepAliveTime}")
    private long keepAliveTime;

    @Bean("parserExecutor")
    public ThreadPoolExecutor threadPoolExecutor() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize,
                maxPoolSize,
                keepAliveTime,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(queueCapacity));
        return threadPoolExecutor;
    }
}