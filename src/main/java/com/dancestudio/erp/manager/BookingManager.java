package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.BookingEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;

import java.util.List;

public interface BookingManager extends BaseManager<BookingEntry, Long> {

    Long countBookingsByStudioId(Long studioId);

    Long countBookingsByStudioIdAndMonth(Long studioId, Long startMonth, Long endMonth);

    List<BookingEntry> getAllBookings(Long studioId, int page, int size, Long startMonth, Long endMonth) throws Exception;

}
