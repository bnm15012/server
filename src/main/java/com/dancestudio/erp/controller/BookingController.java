package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.BookingEntry;
import com.dancestudio.erp.response.BookingResponse;
import com.dancestudio.erp.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
public class BookingController extends BaseController<BookingEntry, BookingResponse, Long> {

    @Autowired
    private BookingService bookingService;

    @Override
    public ResponseEntity<BookingResponse> add(@RequestBody BookingEntry bookingEntry) {
        return bookingService.add(bookingEntry);
    }

    @Override
    public ResponseEntity<BookingResponse> update(@PathVariable Long id, @RequestBody BookingEntry bookingEntry) {
        return bookingService.update(id, bookingEntry);
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return bookingService.delete(id);
    }

    @Override
    public ResponseEntity<BookingResponse> get(@PathVariable Long id) {
        return bookingService.get(id);
    }

    @GetMapping("/getAllBookings/{branchId}/{startMonth}/{endMonth}")
    public ResponseEntity<BookingResponse> getAllBookings(@PathVariable Long branchId,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
            @PathVariable Long startMonth, @PathVariable Long endMonth) {
        return bookingService.getAllBookings(branchId, page, size, startMonth, endMonth);
    }

}
