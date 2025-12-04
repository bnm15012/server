package com.dancestudio.erp.modules.booking;

import com.dancestudio.erp.entry.BranchEntry;
import com.dancestudio.erp.entry.PaymentEntry;
import com.dancestudio.erp.enums.PayeeType;
import com.dancestudio.erp.enums.PaymentStatus;
import com.dancestudio.erp.enums.PaymentType;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.BranchManager;
import com.dancestudio.erp.manager.PaymentManager;
import com.dancestudio.erp.modules.client.ClientEntry;
import com.dancestudio.erp.modules.client.ClientManager;
import com.dancestudio.erp.specification.BookingSpecifications;
import com.dancestudio.erp.util.ConvertToEntryUtil;
import com.dancestudio.erp.util.DateUtil;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
@Setter(onMethod = @__({@Autowired}))
public class BookingManagerImpl implements BookingManager {

    private final BookingRepository bookingRepository;

    private BranchManager branchManager;
    private ClientManager clientManager;
    private PaymentManager paymentManager;

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
        Specification<Booking> spec = Specification.where(BookingSpecifications.hasBranchId(branchId));
        return bookingRepository.count(spec);
    }

    @Override
    public Long countBookingsByBranchIdAndMonth(Long branchId, Integer startMonth, Integer startYear, Integer endMonth, Integer endYear) {
        Map<String, Date> monthRange = DateUtil.getDateRangeByMonthYear(startMonth, startYear, endMonth, endYear);
        Specification<Booking> spec = Specification
                .where(BookingSpecifications.hasBranchId(branchId))
                .and(BookingSpecifications.createdOnBetween(monthRange.get("start"), monthRange.get("end")));
        return bookingRepository.count(spec);
    }

    @Override
    public List<BookingEntry> getAllBookings(Long branchId, Integer page, Integer size,
                                             Integer startDate, Integer startMonth, Integer startYear,
                                             Integer endDate, Integer endMonth, Integer endYear,
                                             String searchTerm) throws Exception {
        Pageable pageable = size == -1
                ? Pageable.unpaged()
                : PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "lastModifiedOn"));

        Specification<Booking> spec = Specification.where(BookingSpecifications.hasBranchId(branchId))
                .and(BookingSpecifications.search(searchTerm));

        if ((startDate != null && startDate > 0) && (endDate != null && endDate > 0)) {
            Map<String, Date> dateRange = DateUtil.getUTCDateRange(startDate, startMonth, startYear, endDate, endMonth, endYear);
            spec = spec.and(BookingSpecifications.createdOnBetween(dateRange.get("start"), dateRange.get("end")));
        } else if (startMonth != null && startMonth > 0 && endMonth != null && endMonth > 0 &&
                   startYear != null && startYear > 0 && endYear != null && endYear > 0) {
            Map<String, Date> monthRange = DateUtil.getDateRangeByMonthYear(startMonth, startYear, endMonth, endYear);
            spec = spec.and(BookingSpecifications.createdOnBetween(monthRange.get("start"), monthRange.get("end")));
        }

        Page<Booking> pageResult = bookingRepository.findAll(spec, pageable);

        List<BookingEntry> bookingEntries = new ArrayList<>();
        for (Booking booking : pageResult) {
            bookingEntries.add(convertToEntry(booking));
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
