package com.dancestudio.erp.service.impl;

import com.dancestudio.erp.entry.BookingEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.BookingManager;
import com.dancestudio.erp.response.BookingResponse;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.service.BookingService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Setter(onMethod = @__({@Autowired}))
@Component
public class BookingServiceImpl implements BookingService {

    private BookingManager bookingManager;

    @Override
    public ResponseEntity<BookingResponse> add(BookingEntry bookingEntry) {
        BookingResponse response = new BookingResponse();

        try {
            BookingEntry entry = bookingManager.add(bookingEntry);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Booking added successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<BookingResponse> update(Long bookingId, BookingEntry bookingEntry) {
        BookingResponse response = new BookingResponse();

        try {
            BookingEntry entry = bookingManager.update(bookingId, bookingEntry);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Booking updated successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (EntityNotFoundException e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<Void> delete(Long bookingId) {
        try {
            bookingManager.delete(bookingId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<BookingResponse> get(Long bookingId) {
        BookingResponse response = new BookingResponse();

        try {
            BookingEntry entry = bookingManager.getById(bookingId);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Booking retrieved successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (EntityNotFoundException e) {
            response.setData(Collections.emptyList());
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<BookingResponse> getAllBookings(Long branchId, int page, int size, Long startMonth, Long endMonth) {
        BookingResponse response = new BookingResponse();

        try {
            List<BookingEntry> entries = bookingManager.getAllBookings(branchId, page, size, startMonth, endMonth);

            long bookingCount = (startMonth.equals(0L) || endMonth.equals(0L))
                    ? bookingManager.countBookingsByBranchId(branchId)
                    : bookingManager.countBookingsByBranchIdAndMonth(branchId, startMonth, endMonth);
            response.setData(entries);
            response.setStatus(new StatusResponse(1, "Bookings retrieved successfully", StatusResponse.Type.SUCCESS, (int) bookingCount));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
