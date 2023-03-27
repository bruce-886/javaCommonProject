package com.example.projectCommon.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilConfig {

    @Value("${util.timezone}")
    private String timeZone;

    public String getTimeZone() {
        return timeZone;
    }
}
