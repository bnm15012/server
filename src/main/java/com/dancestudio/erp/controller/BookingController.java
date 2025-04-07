package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.BookingEntry;
import com.dancestudio.erp.response.BookingResponse;
import com.dancestudio.erp.response.BookingResponse;
import com.dancestudio.erp.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/add")
    public ResponseEntity<BookingResponse> addBooking(@RequestBody BookingEntry bookingEntry) {
        return bookingService.addBooking(bookingEntry);
    }

    @PutMapping("/update/{bookingId}")
    public ResponseEntity<BookingResponse> updateBooking(@PathVariable Long bookingId, @RequestBody BookingEntry bookingEntry) {
        return bookingService.updateBooking(bookingId, bookingEntry);
    }

    @DeleteMapping("/delete/{bookingId}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long bookingId) {
        return bookingService.deleteBooking(bookingId);
    }

    @GetMapping("/get/{bookingId}")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable Long bookingId) {
        return bookingService.getBookingById(bookingId);
    }

    @GetMapping("/getAllBookings/{studioId}/{startMonth}/{endMonth}")
    public ResponseEntity<BookingResponse> getAllBookings(
            @PathVariable Long studioId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable Long startMonth,
            @PathVariable Long endMonth) {
        return bookingService.getAllBookings(studioId, page, size, startMonth, endMonth);
    }

}
