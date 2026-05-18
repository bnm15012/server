package com.dancestudio.erp.modules.member.attendance;

import com.dancestudio.erp.context.TimeZoneContext;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class AttendanceEntryConverter {

    private static final int MAX_DAYS = 368;

    /**
     * Convert bitmap -> attendance entries
     */
    public static List<AttendanceEntry> bitmapToEntries(
            byte[] attendanceBitmap,
            Date startDate) {

        List<AttendanceEntry> entries = new ArrayList<>();

        if (attendanceBitmap == null || attendanceBitmap.length == 0) {
            return entries;
        }

        BitSet bitSet = BitSet.valueOf(attendanceBitmap);

        LocalDate start = startDate.toInstant()
                .atZone(TimeZoneContext.getTimeZone())
                .toLocalDate();

        for (int i = 0; i < MAX_DAYS; i++) {

            if (bitSet.get(i)) {

                LocalDate attendanceDate = start.plusDays(i);

                Date date = Date.from(
                        attendanceDate
                                .atStartOfDay(TimeZoneContext.getTimeZone())
                                .toInstant());

                entries.add(new AttendanceEntry(date, true));
            }
        }

        return entries;
    }

    /**
     * Convert attendance entries -> bitmap
     */
    public static byte[] entriesToBitmap(
            List<AttendanceEntry> entries,
            Date startDate) {

        if (entries == null || entries.isEmpty()) {
            return new byte[0];
        }

        BitSet bitSet = new BitSet();

        LocalDate start = startDate.toInstant()
                .atZone(TimeZoneContext.getTimeZone())
                .toLocalDate();

        for (AttendanceEntry entry : entries) {

            if (!entry.isPresent()) {
                continue;
            }

            LocalDate attendanceDate = entry.getDate()
                    .toInstant()
                    .atZone(TimeZoneContext.getTimeZone())
                    .toLocalDate();

            long index = ChronoUnit.DAYS.between(start, attendanceDate);

            if (index < 0 || index >= MAX_DAYS) {
                throw new IllegalArgumentException(
                        "Attendance date out of valid range: " + entry.getDate());
            }

            bitSet.set((int) index, true);
        }

        return bitSet.toByteArray();
    }
}