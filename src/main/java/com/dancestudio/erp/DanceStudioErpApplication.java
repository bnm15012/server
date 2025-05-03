package com.dancestudio.erp;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
//@EnableScheduling
public class DanceStudioErpApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC")); 
        SpringApplication.run(DanceStudioErpApplication.class, args);
    }

}