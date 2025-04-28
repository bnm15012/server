package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.Booking;
import com.dancestudio.erp.entry.*;
import com.dancestudio.erp.enums.PayeeType;
import com.dancestudio.erp.enums.PaymentStatus;
import com.dancestudio.erp.enums.PaymentType;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.*;
import com.dancestudio.erp.repository.BookingRepository;
import com.dancestudio.erp.util.ConvertToEntryUtil;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Setter
public class BookingManagerImpl implements BookingManager {

    private final BookingRepository bookingRepository;

    @Autowired
    private BranchManager branchManager;

    @Autowired
    private ClientManager clientManager;

    @Autowired
    private PaymentManager paymentManager;


    @Autowired
    public BookingManagerImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public BookingEntry add(BookingEntry bookingEntry) throws Exception {
        validateRequest(bookingEntry);
        branchManager.getById(bookingEntry.getBranchId());
        clientManager.getById(bookingEntry.getClientEntry().getClientId());

        Booking booking = convertToEntity(bookingEntry, null);
        booking = bookingRepository.save(booking);
        try {
            Long payeeId = booking.getId();
            bookingEntry.getPaymentEntry().setPayeeId(payeeId);
            paymentManager.add(bookingEntry.getPaymentEntry());
        } catch (Exception ex) {
            throw new EntityNotFoundException("Failed to add payment details");
        }
        return convertToEntry(booking);
    }

    private void validateRequest(BookingEntry bookingEntry) {
        if (Objects.isNull(bookingEntry.getTotalAmount()) || bookingEntry.getTotalAmount() <= 0) {
            throw new IllegalArgumentException("Total amount must be greater than zero");
        }
        if (Objects.isNull(bookingEntry.getAdvanceAmount()) || bookingEntry.getAdvanceAmount() <= 0) {
            throw new IllegalArgumentException("Advance amount must be greater than zero");
        }
        if (Objects.isNull(bookingEntry.getBalanceAmount())) {
            if (bookingEntry.getTotalAmount().equals(bookingEntry.getAdvanceAmount())) {
                throw new IllegalArgumentException("Advance amount should be equal to total amount");
            }
        }
        if (bookingEntry.getBalanceAmount() < 0) {
            throw new IllegalArgumentException("Balance amount cannot be negative");
        }
        if (bookingEntry.getAdvanceAmount() + bookingEntry.getBalanceAmount() != bookingEntry.getTotalAmount()) {
            throw new IllegalArgumentException("Advance amount + Balance amount should be equal to Total amount");
        }
    }

    @Override
    public BookingEntry update(Long bookingId, BookingEntry bookingEntry) throws Exception {
        validateRequest(bookingEntry);
        Booking existingBooking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));

        Booking updatedBooking = convertToEntity(bookingEntry, existingBooking);
        return convertToEntry(bookingRepository.save(updatedBooking));
    }

    @Override
    public void delete(Long bookingId) throws EntityNotFoundException {
        bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));

        PaymentEntry paymentEntry = paymentManager.getPaymentByPayeeIdAndPayeeType(bookingId, PayeeType.BOOKING);
        try {
            paymentManager.delete(Long.valueOf(paymentEntry.getPaymentId()));
        } catch (Exception e) {
            throw new EntityNotFoundException("Failed to delete payment details");
        }
        bookingRepository.deleteById(bookingId);
    }

    @Override
    public BookingEntry getById(Long bookingId) throws Exception {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));

        return convertToEntry(booking);
    }

    @Override
    public Long countBookingsByBranchId(Long branchId) {
        return bookingRepository.countBookingsByBranchId(branchId);
    }

    @Override
    public Long countBookingsByBranchIdAndMonth(Long branchId, Long startMonth, Long endMonth) {
        return bookingRepository.countBookingsByBranchIdAndMonthLong(branchId, startMonth, endMonth);
    }

    @Override
    public List<BookingEntry> getAllBookings(Long branchId, int page, int size, Long startMonth, Long endMonth) throws Exception {
        Page<Booking> entries;
        Pageable pageable = PageRequest.of(page, size);
        if (startMonth.equals(0L) || endMonth.equals(0L)) {
            entries = bookingRepository.findBookingsByBranchId(branchId, pageable);
        } else {
            entries = bookingRepository.findAllByBranchId(branchId, startMonth, endMonth, pageable);
        }

        List<BookingEntry> bookingEntries = new ArrayList<>();
        for (Booking entry : entries) {
            BookingEntry bookingEntry = convertToEntry(entry);
            bookingEntries.add(bookingEntry);
        }

        return bookingEntries;
    }

    public BookingEntry convertToEntry(Booking booking) throws Exception {
        BookingEntry bookingEntry = new BookingEntry();

        bookingEntry.setId(booking.getId());
        bookingEntry.setBranchId(booking.getBranch().getId());
        bookingEntry.setPurpose(booking.getPurpose());
        bookingEntry.setTotalAmount(booking.getTotalAmount());
        bookingEntry.setPaymentStatus(PaymentStatus.valueOf(booking.getPaymentStatus()));
        bookingEntry.setAdvanceAmount(booking.getAdvanceAmount());
        bookingEntry.setBalanceAmount(booking.getBalanceAmount());
        bookingEntry.setNotes(booking.getNotes());
        bookingEntry.setBookingDate(booking.getBookingDate());
        bookingEntry.setStartTime(booking.getStartTime());
        bookingEntry.setEndTime(booking.getEndTime());
        bookingEntry.setAdvanceDate(booking.getAdvanceDate());
        bookingEntry.setAdvanceMode(PaymentType.valueOf(booking.getAdvanceMode()));
        bookingEntry.setFinalPaymentDate(booking.getFinalPaymentDate());
        bookingEntry.setPaymentMode(PaymentType.valueOf(booking.getPaymentMode()));

        if (Objects.nonNull(booking.getClient())) {
            ClientEntry clientEntry = clientManager.getById(booking.getClient().getId());
            bookingEntry.setClientEntry(clientEntry);
        }
        return bookingEntry;
    }

    private Booking convertToEntity(BookingEntry bookingEntry, Booking existingBooking) throws Exception {
        Booking booking = (existingBooking != null) ? existingBooking : new Booking();

        if (Objects.nonNull(bookingEntry.getBranchId())) {
            BranchEntry branchEntry = branchManager.getById(bookingEntry.getBranchId());
            booking.setBranch(ConvertToEntryUtil.convertToEntity(branchEntry, null));
        }
        if (Objects.nonNull(bookingEntry.getClientEntry())) {
            ClientEntry clientEntry = clientManager.getById(bookingEntry.getClientEntry().getClientId());
            booking.setClient(ConvertToEntryUtil.convertToEntity(clientEntry, null));
        }

        Optional.ofNullable(bookingEntry.getPurpose()).ifPresent(booking::setPurpose);
        Optional.ofNullable(bookingEntry.getTotalAmount()).ifPresent(booking::setTotalAmount);
        Optional.ofNullable(bookingEntry.getPaymentStatus()).ifPresent(status -> booking.setPaymentStatus(status.name()));
        Optional.ofNullable(bookingEntry.getAdvanceAmount()).ifPresent(booking::setAdvanceAmount);
        Optional.ofNullable(bookingEntry.getBalanceAmount()).ifPresent(booking::setBalanceAmount);
        Optional.ofNullable(bookingEntry.getNotes()).ifPresent(booking::setNotes);
        Optional.ofNullable(bookingEntry.getBookingDate()).ifPresent(booking::setBookingDate);
        Optional.ofNullable(bookingEntry.getStartTime()).ifPresent(booking::setStartTime);
        Optional.ofNullable(bookingEntry.getEndTime()).ifPresent(booking::setEndTime);
        Optional.ofNullable(bookingEntry.getAdvanceDate()).ifPresent(booking::setAdvanceDate);
        Optional.of(bookingEntry.getAdvanceMode().name()).ifPresent(booking::setAdvanceMode);
        Optional.ofNullable(bookingEntry.getFinalPaymentDate()).ifPresent(booking::setFinalPaymentDate);
        Optional.ofNullable(bookingEntry.getPaymentMode()).ifPresent(mode -> booking.setPaymentMode(mode.name()));

        return booking;
    }
}
