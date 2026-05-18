package com.dancestudio.erp.modules.member.attendance;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.BitSet;
import java.util.Date;

import com.dancestudio.erp.context.TimeZoneContext;

public class AttendanceUtils {
    public static byte[] toggleAttendance(byte[] attendanceBitmap, Date membershipStartDate, Date membershipEndDate, Date date, boolean preset) {
        long index = getDayIndex(membershipStartDate, date);
        if (index < 0 || index >= 368) {
            throw new IllegalArgumentException("Date is out of valid range for attendance (0-365 days from start date).");
        }
        
        if (date.after(membershipEndDate)) {
            throw new IllegalStateException("Cannot mark attendance after membership expiration.");
        }

        BitSet bitSet = attendanceBitmap != null ? BitSet.valueOf(attendanceBitmap) : new BitSet();

        if (preset) {
            bitSet.set((int) index);
        } else {
            bitSet.clear((int) index);
        }
        
        byte[] bytes = bitSet.toByteArray();
        if (bytes.length > 48) {
            throw new IllegalStateException("Attendance bitmap exceeds maximum size of 48 bytes.");
        }
        return bytes;
    }

    public static long getDayIndex(Date membershipStartDate, Date date) {
        LocalDate startDate = membershipStartDate.toInstant()
                .atZone(TimeZoneContext.getTimeZone())
                .toLocalDate();

        LocalDate targetDate = date.toInstant()
                .atZone(TimeZoneContext.getTimeZone())
                .toLocalDate();
        return ChronoUnit.DAYS.between(startDate, targetDate);
    }
}
