package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.BookingEntry;

import java.util.List;

public interface BookingManager extends BaseManager<BookingEntry, Long> {

    Long countBookingsByBranchId(Long branchId);

    Long countBookingsByBranchIdAndMonth(Long branchId, Long startMonth, Long endMonth);

    List<BookingEntry> getAllBookings(Long branchId, int page, int size, Long startMonth, Long endMonth) throws Exception;

}
