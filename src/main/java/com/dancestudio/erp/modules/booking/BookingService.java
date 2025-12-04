package com.dancestudio.erp.modules.booking;

import com.dancestudio.erp.response.BookingResponse;
import com.dancestudio.erp.service.BaseService;

import org.springframework.http.ResponseEntity;

public interface BookingService extends BaseService<BookingEntry, BookingResponse, Long> {

    ResponseEntity<BookingResponse> getAllBookings(Long branchId, Integer page, Integer size, Integer startMonth, Integer startYear, Integer endMonth, Integer endYear, String searchTerm);

}
