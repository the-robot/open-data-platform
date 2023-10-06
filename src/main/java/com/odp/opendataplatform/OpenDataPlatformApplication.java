package com.odp.opendataplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OpenDataPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenDataPlatformApplication.class, args);
    }

}
