package com.dancestudio.erp.util;

import com.dancestudio.erp.entry.SessionEntry;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
@Setter
@Slf4j
public class WhatsappUtil {

    @Value("${ultramsg.api.url}")
    private String baseUrl;

    @Value("${create.session.url}")
    private String createSessionUrl;

    public boolean sendMessage(String token, String instanceId, String to, String body) {
        String apiUrl = baseUrl + "/" + instanceId + "/messages/chat";
        try {
            String urlParameters = "token=" + token + "&to=" + to + "&body=" + body;

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = urlParameters.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;

        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public SessionEntry createSession(Long branchId) {
        SessionEntry sessionEntry = new SessionEntry();
        String apiUrl = createSessionUrl + "/login/" + branchId;
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    ObjectMapper objectMapper = new ObjectMapper();
                    return objectMapper.readValue(response.toString(), SessionEntry.class);
                }
            } else {
                log.error("Failed to create session: HTTP " + responseCode);
                sessionEntry.setData(null);
                sessionEntry.setMessage("Whatsapp socket hang up !");
                sessionEntry.setSuccess(false);
                return sessionEntry;
            }
        } catch (Exception e) {
            log.error("Error occurred while creating session: {}", e.getMessage());
            sessionEntry.setData(null);
            sessionEntry.setMessage("Internal server error !");
            sessionEntry.setSuccess(false);
            return sessionEntry;
        }
    }

    public SessionEntry logoutSession(Long branchId) {
        SessionEntry sessionEntry = new SessionEntry();
        String apiUrl = createSessionUrl + "/logout/" + branchId;
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    ObjectMapper objectMapper = new ObjectMapper();
                    return objectMapper.readValue(response.toString(), SessionEntry.class);
                }
            } else {
                log.error("Failed to logout session: HTTP " + responseCode);
                sessionEntry.setData(null);
                sessionEntry.setMessage("Whatsapp socket hang up !");
                sessionEntry.setSuccess(false);
                return sessionEntry;
            }
        } catch (Exception e) {
            log.error("Error occurred while logging out session: {}", e.getMessage());
            sessionEntry.setData(null);
            sessionEntry.setMessage("Internal server error !");
            sessionEntry.setSuccess(false);
            return sessionEntry;
        }
    }

    public boolean sendMessage(String number, String message, Long branchId) {
        createSession(branchId);
        String apiUrl = createSessionUrl + "/send/" + branchId;

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> payload = new HashMap<>();
            payload.put("numbers", Collections.singletonList(number));
            payload.put("message", message);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = mapper.writeValueAsBytes(payload);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                return true;
            } else {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                    String line;
                    StringBuilder response = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                    log.error("Failed to send message. Response: {}", response.toString());
                }
                return false;
            }

        } catch (Exception e) {
            log.error("Error occurred while sending message", e);
            return false;
        }
    }

}