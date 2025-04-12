package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.Booking;
import com.dancestudio.erp.entry.BookingEntry;
import com.dancestudio.erp.entry.ClientEntry;
import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.enums.PaymentStatus;
import com.dancestudio.erp.enums.PaymentType;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.BookingManager;
import com.dancestudio.erp.manager.ClientManager;
import com.dancestudio.erp.manager.PaymentManager;
import com.dancestudio.erp.manager.StudioManager;
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
    private StudioManager studioManager;

    @Autowired
    private ClientManager clientManager;

    @Autowired
    private PaymentManager paymentManager;


    @Autowired
    public BookingManagerImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public BookingEntry addBooking(BookingEntry bookingEntry) throws EntityNotFoundException {
        validateRequest(bookingEntry);
        studioManager.getStudioById(bookingEntry.getStudioId());
        clientManager.getClientById(bookingEntry.getClientId());

        Booking booking = convertToEntity(bookingEntry, null);

        try {
            paymentManager.addPayment(bookingEntry.getPaymentEntry());
        } catch (Exception ex) {
            throw new EntityNotFoundException("Failed to add payment details");
        }
        return convertToEntry(bookingRepository.save(booking));
    }

    private void validateRequest(BookingEntry bookingEntry) {
        if(Objects.isNull(bookingEntry.getBalanceAmount())) {
            if (Objects.isNull(bookingEntry.getTotalAmount()) || bookingEntry.getTotalAmount() <= 0) {
                throw new IllegalArgumentException("Total amount must be greater than zero");
            }
            if (Objects.isNull(bookingEntry.getAdvanceAmount()) || bookingEntry.getAdvanceAmount() <= 0) {
                throw new IllegalArgumentException("Advance amount must be greater than zero");
            }
            if (!bookingEntry.getTotalAmount().equals(bookingEntry.getAdvanceAmount())) {
                throw new IllegalArgumentException("Advance amount should be equal to total amount");
            }

        }
    }

    @Override
    public BookingEntry updateBooking(Long bookingId, BookingEntry bookingEntry) throws EntityNotFoundException {
        Booking existingBooking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));

        Booking updatedBooking = convertToEntity(bookingEntry, existingBooking);
        return convertToEntry(bookingRepository.save(updatedBooking));
    }

    @Override
    public void deleteBooking(Long bookingId) throws EntityNotFoundException {
        bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));

        bookingRepository.deleteById(bookingId);
    }

    @Override
    public BookingEntry getBookingById(Long bookingId) throws EntityNotFoundException {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));

        return convertToEntry(booking);
    }

    @Override
    public Long countBookingsByStudioId(Long studioId) {
        return bookingRepository.countBookingsByStudioId(studioId);
    }

    @Override
    public Long countBookingsByStudioIdAndMonth(Long studioId, Long startMonth, Long endMonth) {
        return bookingRepository.countBookingsByStudioIdAndMonthLong(studioId, startMonth, endMonth);
    }

    @Override
    public List<BookingEntry> getAllBookings(Long studioId, int page, int size, Long startMonth, Long endMonth) throws EntityNotFoundException {
        Page<Booking> entries;
        Pageable pageable = PageRequest.of(page, size);
        if (startMonth.equals(0L) || endMonth.equals(0L)) {
            entries = bookingRepository.findBookingsByStudioId(studioId, pageable);
        } else {
            entries = bookingRepository.findAllByStudioId(studioId, startMonth, endMonth, pageable);
        }

        List<BookingEntry> bookingEntries = new ArrayList<>();
        for (Booking entry : entries) {
            BookingEntry bookingEntry = convertToEntry(entry);
            bookingEntries.add(bookingEntry);
        }

        return bookingEntries;
    }

    public BookingEntry convertToEntry(Booking booking) throws EntityNotFoundException {
        BookingEntry bookingEntry = new BookingEntry();

        bookingEntry.setId(booking.getId());
        bookingEntry.setStudioId(booking.getStudio().getId());
        bookingEntry.setClientId(booking.getClient().getId());
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

        return bookingEntry;
    }

    private Booking convertToEntity(BookingEntry bookingEntry, Booking existingBooking) throws EntityNotFoundException {
        Booking booking = (existingBooking != null) ? existingBooking : new Booking();

        if (Objects.nonNull(bookingEntry.getStudioId())) {
            StudioEntry studioEntry = studioManager.getStudioById(bookingEntry.getStudioId());
            booking.setStudio(ConvertToEntryUtil.convertToEntity(studioEntry, null));
        }
        if (Objects.nonNull(bookingEntry.getClientId())) {
            ClientEntry clientEntry = clientManager.getClientById(bookingEntry.getClientId());
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
