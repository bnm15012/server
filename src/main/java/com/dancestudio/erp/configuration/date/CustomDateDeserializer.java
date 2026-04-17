package com.dancestudio.erp.configuration.date;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.dancestudio.erp.context.TimeZoneContext;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CustomDateDeserializer extends JsonDeserializer<Date> {

    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {

        String value = p.getText();
        if (value == null || value.isEmpty()) return null;

        ZoneId clientZone = TimeZoneContext.getTimeZone();

        LocalDateTime localDateTime = LocalDateTime.parse(value,
                DateTimeFormatter.ofPattern(FORMAT));

        Instant utcInstant = localDateTime
                .atZone(clientZone)
                .withZoneSameInstant(ZoneId.of("UTC"))
                .toInstant();

        return Date.from(utcInstant);
    }
}
