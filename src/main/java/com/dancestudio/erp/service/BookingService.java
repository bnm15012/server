package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.BookingEntry;
import com.dancestudio.erp.response.BookingResponse;
import org.springframework.http.ResponseEntity;

public interface BookingService extends BaseService<BookingEntry, BookingResponse, Long> {

    ResponseEntity<BookingResponse> getAllBookings(Long branchId, Integer page, Integer size, Integer startMonth, Integer startYear, Integer endMonth, Integer endYear);

}
