package com.dancestudio.erp.util;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;

@Service
public class GeoLocationUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeoLocationUtil.class);
    private static final String GEOLOCATION_API_URL = "https://ipapi.co/";
    private static final String COUNTRY_ENDPOINT = "/country/";
    private static final String DEFAULT_COUNTRY_CODE = "IN";
    private static final String UNKNOWN_IP = "undefined";

    public String getCountryCode(String ipAddress) {
        if (isLocalOrPrivateIp(ipAddress)) {
            LOGGER.info("IP {} is local/private/invalid, using default country code: {}", ipAddress, DEFAULT_COUNTRY_CODE);
            return DEFAULT_COUNTRY_CODE;
        }

        String apiUrl = GEOLOCATION_API_URL + ipAddress + COUNTRY_ENDPOINT;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "DanceStudioERP");
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    String response = reader.readLine();
                    LOGGER.info("Get CountryCode Response : {}", response);
                    return (response == null || UNKNOWN_IP.equalsIgnoreCase(response.trim())) ? DEFAULT_COUNTRY_CODE : response.trim();
                }
            } else {
                LOGGER.warn("Geolocation API returned status {} for IP: {}", responseCode, ipAddress);
                return DEFAULT_COUNTRY_CODE;
            }
        } catch (Exception e) {
            LOGGER.error("Error fetching country code for IP: {}", ipAddress, e);
            return DEFAULT_COUNTRY_CODE;
        }
    }

    public boolean isLocalOrPrivateIp(String ipAddress) {
        if (ipAddress == null || ipAddress.isBlank() || UNKNOWN_IP.equalsIgnoreCase(ipAddress) || "localhost".equalsIgnoreCase(ipAddress)) {
            return true;
        }
        try {
            InetAddress address = InetAddress.getByName(ipAddress);
            return address.isLoopbackAddress() || address.isSiteLocalAddress() || address.isLinkLocalAddress() || address.isAnyLocalAddress();
        } catch (Exception e) {
            return true;
        }
    }

    public String extractClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            LOGGER.info("Remote address : {}", request.getRemoteAddr());
            return request.getRemoteAddr();
        }
        LOGGER.info("IP address : {}", ip);
        return ip.split(",")[0].trim();
    }
}