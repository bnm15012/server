package com.dancestudio.erp.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    /**
     * Retrieves the UTC date range based on the given start and end month/year and
     * user's time zone.
     * The resulting start and end dates are in UTC but reflect the user's local
     * month boundaries.
     *
     * @param startMonth The start month (1 = January, 12 = December)
     * @param startYear  The start year
     * @param endMonth   The end month (1 = January, 12 = December)
     * @param endYear    The end year
     * @param zoneId     The time zone to base the range on (e.g., IST)
     * @return A Map with UTC "start" and "end" Date values representing the user's
     *         local month boundaries
     */
    public static Map<String, Date> getRange(int startMonth, int startYear, int endMonth, int endYear, ZoneId zoneId) {
        if (zoneId == null) {
            zoneId = ZoneOffset.UTC;
        }

        LocalDate localStartDate = YearMonth.of(startYear, startMonth).atDay(1);
        ZonedDateTime zonedStart = localStartDate.atStartOfDay(zoneId);
        Instant startInstant = zonedStart.toInstant();
        Date startDate = Date.from(startInstant);

        LocalDate localEndDate = YearMonth.of(endYear, endMonth).atEndOfMonth();
        ZonedDateTime zonedEnd = localEndDate.atTime(23, 59, 59).atZone(zoneId);
        Instant endInstant = zonedEnd.toInstant();
        Date endDate = Date.from(endInstant);

        Map<String, Date> result = new HashMap<>();
        result.put("start", startDate);
        result.put("end", endDate);

        return result;
    }

    public static boolean isToday(Date date) {
        if (date == null)
            return false;

        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return LocalDate.now().equals(localDate);
    }
}
