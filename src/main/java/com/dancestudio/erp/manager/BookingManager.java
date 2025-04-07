package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.BookingEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;

import java.util.List;

public interface BookingManager {

    BookingEntry addBooking(BookingEntry bookingEntry) throws EntityNotFoundException;

    BookingEntry updateBooking(Long bookingId, BookingEntry bookingEntry) throws EntityNotFoundException;

    void deleteBooking(Long bookingId) throws EntityNotFoundException;

    BookingEntry getBookingById(Long bookingId) throws EntityNotFoundException;

    Long countBookingsByStudioId(Long studioId);

    Long countBookingsByStudioIdAndMonth(Long studioId, Long startMonth, Long endMonth);

    List<BookingEntry> getAllBookings(Long studioId, int page, int size, Long startMonth, Long endMonth) throws EntityNotFoundException;

}
