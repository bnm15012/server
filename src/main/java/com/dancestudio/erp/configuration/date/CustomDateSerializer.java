package com.dancestudio.erp.configuration.date;

import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.dancestudio.erp.context.TimeZoneContext;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomDateSerializer extends JsonSerializer<Date> {

    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Override
    public void serialize(Date value, JsonGenerator gen,
            SerializerProvider serializers) throws IOException {

        if (value == null) {
            gen.writeNull();
            return;
        }

        ZoneId clientZone = TimeZoneContext.getTimeZone();

        String formatted = value.toInstant()
                .atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(clientZone)
                .format(DateTimeFormatter.ofPattern(FORMAT));

        gen.writeString(formatted);
    }
}
