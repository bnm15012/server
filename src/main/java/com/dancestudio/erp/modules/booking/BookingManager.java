package com.dancestudio.erp.modules.booking;

import com.dancestudio.erp.manager.BaseManagerInt;

import java.util.List;

public interface BookingManager extends BaseManagerInt<BookingEntry, Long> {

    Long countBookingsByBranchId(Long branchId);

    Long countBookingsByBranchIdAndMonth(Long branchId, Integer startMonth, Integer startYear, Integer endMonth, Integer endYear);

    List<BookingEntry> getAllBookings(Long branchId, Integer page, Integer size, Integer startDate, Integer startMonth, Integer startYear, Integer endDate, Integer endMonth, Integer endYear, String searchTerm) throws Exception;

}
