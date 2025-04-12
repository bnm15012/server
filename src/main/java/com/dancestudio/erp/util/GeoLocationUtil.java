package com.dancestudio.erp.util;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class GeoLocationUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeoLocationUtil.class);
    private static final String GEOLOCATION_API_URL = "https://ipapi.co/";
    private static final String COUNTRY_ENDPOINT = "/country/";
    private static final String DEFAULT_COUNTRY_CODE = "IN";
    private static final String UNKNOWN_IP = "undefined";

    public String getCountryCode(String ipAddress) {
        String apiUrl = GEOLOCATION_API_URL + ipAddress + COUNTRY_ENDPOINT;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();
            conn.setRequestMethod("GET");

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String response = reader.readLine();
                return (response == null || UNKNOWN_IP.equalsIgnoreCase(response)) ? DEFAULT_COUNTRY_CODE : response;
            }
        } catch (Exception e) {
            LOGGER.error("Error fetching country code for IP: {}", ipAddress, e);
            return DEFAULT_COUNTRY_CODE;
        }
    }

    public String extractClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            return request.getRemoteAddr();
        }
        return ip.split(",")[0].trim();
    }
}