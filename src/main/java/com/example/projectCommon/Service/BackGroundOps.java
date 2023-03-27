package com.example.projectCommon.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
@EnableAsync
public class BackGroundOps {

    private static final Logger logger = LoggerFactory.getLogger(BackGroundOps.class);
    @Async("asyncServiceExecutor")
//    @Scheduled(cron = "${cron.daily}", zone = "${cron.timezone}")
    public String test() {
        System.out.println("Execute method with configured executor - " + Thread.currentThread().getName());
        return "123";
    }
}
