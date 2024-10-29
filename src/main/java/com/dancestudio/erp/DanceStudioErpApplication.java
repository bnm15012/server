package com.dancestudio.erp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DanceStudioErpApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DanceStudioErpApplication.class, args);
    }

}