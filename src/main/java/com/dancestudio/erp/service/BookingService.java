package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.BookingEntry;
import com.dancestudio.erp.response.BookingResponse;
import org.springframework.http.ResponseEntity;

public interface BookingService extends BaseService<BookingEntry, BookingResponse, Long> {

    ResponseEntity<BookingResponse> getAllBookings(Long studioId, int page, int size, Long startMonth, Long endMonth);

}
