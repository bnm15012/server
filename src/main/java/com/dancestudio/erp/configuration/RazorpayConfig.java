package com.dancestudio.erp.configuration;

import com.razorpay.RazorpayClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RazorpayConfig {

    @Value("${razorpay.api_key}")
    private String razorpayApiKey;

    @Value("${razorpay.api_secret}")
    private String razorpaySecret;

    @Bean
    public RazorpayClient razorpayClient() throws Exception {
        return new RazorpayClient(razorpayApiKey, razorpaySecret);
    }

}