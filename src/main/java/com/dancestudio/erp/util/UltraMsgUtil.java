package com.dancestudio.erp.util;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Component
@Setter
@Slf4j
public class UltraMsgUtil {

    @Value("${ultramsg.api.url}")
    private String baseUrl;

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
}