package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.BookingEntry;
import com.dancestudio.erp.response.BookingResponse;
import org.springframework.http.ResponseEntity;

public interface BookingService {

    ResponseEntity<BookingResponse> addBooking(BookingEntry bookingEntry);

    ResponseEntity<BookingResponse> updateBooking(Long bookingId, BookingEntry bookingEntry);

    ResponseEntity<Void> deleteBooking(Long bookingId);

    ResponseEntity<BookingResponse> getBookingById(Long bookingId);

}
