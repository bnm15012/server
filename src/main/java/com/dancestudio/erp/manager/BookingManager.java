package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.BookingEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;

public interface BookingManager {

    BookingEntry addBooking(BookingEntry bookingEntry) throws EntityNotFoundException;

    BookingEntry updateBooking(Long bookingId, BookingEntry bookingEntry) throws EntityNotFoundException;

    void deleteBooking(Long bookingId) throws EntityNotFoundException;

    BookingEntry getBookingById(Long bookingId) throws EntityNotFoundException;
}
