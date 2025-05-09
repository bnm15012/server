package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.BookingEntry;

import java.util.List;

public interface BookingManager extends BaseManager<BookingEntry, Long> {

    Long countBookingsByBranchId(Long branchId);

    Long countBookingsByBranchIdAndMonth(Long branchId, Integer startMonth, Integer startYear, Integer endMonth, Integer endYear);

    List<BookingEntry> getAllBookings(Long branchId, Integer page, Integer size, Integer startMonth, Integer startYear, Integer endMonth, Integer endYear) throws Exception;

}
