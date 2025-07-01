package com.dancestudio.erp.util;

import com.dancestudio.erp.context.TimeZoneContext;

import java.time.*;
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

    public static Map<String, Date> getDateRangeByMonthYear(int startMonth, int startYear, int endMonth, int endYear) {
        return getDateRange(1, startMonth, startYear, 31, endMonth, endYear);
    }

    /**
     * Retrieves the UTC date range based on the given start/end day/month/year and
     * the user's time zone. The resulting start and end dates are in UTC but
     * reflect
     * the user's local time.
     *
     * @param startDate  The start day of the month
     * @param startMonth The start month (1 = January, 12 = December)
     * @param startYear  The start year
     * @param endDate    The end day of the month
     * @param endMonth   The end month (1 = January, 12 = December)
     * @param endYear    The end year
     * @return A Map with UTC "start" and "end" Date values
     */
    public static Map<String, Date> getDateRange(int startDate, int startMonth, int startYear,
            int endDate, int endMonth, int endYear) {
        ZoneId zoneId = TimeZoneContext.getTimeZone();

        // Validate and clamp dates within valid month ranges
        YearMonth startYM = YearMonth.of(startYear, startMonth);
        YearMonth endYM = YearMonth.of(endYear, endMonth);

        int validStartDay = Math.min(startDate, startYM.lengthOfMonth());
        int validEndDay = Math.min(endDate, endYM.lengthOfMonth());

        LocalDate localStartDate = LocalDate.of(startYear, startMonth, validStartDay);
        LocalDate localEndDate = LocalDate.of(endYear, endMonth, validEndDay);

        ZonedDateTime zonedStart = localStartDate.atStartOfDay(zoneId);
        ZonedDateTime zonedEnd = localEndDate.atTime(23, 59, 59).atZone(zoneId);

        Date utcStart = Date.from(zonedStart.toInstant());
        Date utcEnd = Date.from(zonedEnd.toInstant());

        Map<String, Date> result = new HashMap<>();
        result.put("start", utcStart);
        result.put("end", utcEnd);

        return result;
    }

    public static Map<String, Date> getDateRangeByYear(int startYear, int endYear) {
        return getDateRangeByMonthYear(1, startYear, 12, endYear);
    }

    public static boolean isTodaysDate(Date date) {
        if (date == null) {
            return false;
        }
        LocalDate inputDate = date.toInstant().atZone(ZoneOffset.UTC).toLocalDate();
        LocalDate updatedDate = inputDate.plusDays(1);
        LocalDate currentDateUTC = LocalDate.now(ZoneOffset.UTC);
        return updatedDate.getMonth() == currentDateUTC.getMonth() && updatedDate.getDayOfMonth() == currentDateUTC.getDayOfMonth();
    }
}
