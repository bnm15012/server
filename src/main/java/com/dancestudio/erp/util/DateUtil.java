package com.dancestudio.erp.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

public class DateUtil {
    
    public static Date addDays(Date date, int days) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }

        LocalDate localDate = date.toInstant().atZone(ZoneOffset.UTC).toLocalDate();
        LocalDate updatedLocalDate = localDate.plusDays(days);

        return Date.from(updatedLocalDate.atStartOfDay(ZoneOffset.UTC).toInstant());
    }
    
    public static Date getCurrentDateUTC() {
        return Date.from(Instant.now());
    }
}
