package com.example.projectCommon.Config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

    private static final Logger logger = LoggerFactory.getLogger(AsyncConfig.class);


    @Value("${async.executor.thread.core_pool_size}")
    private int corePoolSize;

    @Bean(name = "asyncServiceExecutor")
    public Executor asyncServiceExecutor() {

        logger.info("Start asyncServiceExecutor");

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(corePoolSize);

        executor.setMaxPoolSize(10);

        executor.setQueueCapacity(10);

        executor.setThreadNamePrefix("TEST");

        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());

        executor.initialize();

        return executor;
    }
}
