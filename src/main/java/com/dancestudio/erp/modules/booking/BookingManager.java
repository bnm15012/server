package com.dancestudio.erp.modules.booking;

import com.dancestudio.erp.base.BaseManager;
import com.dancestudio.erp.enums.PayeeType;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.modules.client.ClientRepository;
import com.dancestudio.erp.modules.invoiceToken.InvoiceToken;
import com.dancestudio.erp.modules.invoiceToken.InvoiceTokenManager;
import com.dancestudio.erp.modules.invoiceToken.InvoiceTokenResponse;
import com.dancestudio.erp.modules.payments.PaymentConvertor;
import com.dancestudio.erp.modules.payments.PaymentManager;
import com.dancestudio.erp.modules.payments.entity.PaymentBooking;
import com.dancestudio.erp.modules.payments.entry.PaymentEntry;
import com.dancestudio.erp.repository.BranchRepository;
import com.dancestudio.erp.specification.BookingSpecifications;
import com.dancestudio.erp.util.DateUtil;
import lombok.Setter;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Setter(onMethod = @__({ @Autowired }))
@Transactional(rollbackFor = Exception.class)
public class BookingManager extends BaseManager<Booking, Long, BookingEntry> {

    private final ClientRepository clientRepository;

    private final BranchRepository branchRepository;

    private final BookingRepository bookingRepository;

    private PaymentManager paymentManager;

    private InvoiceTokenManager invoiceTokenManager;

    public BookingManager(BookingRepository bookingRepository, BranchRepository branchRepository,
            ClientRepository clientRepository, InvoiceTokenManager invoiceTokenManager) {
        super(bookingRepository, "Booking");
        this.bookingRepository = bookingRepository;
        this.branchRepository = branchRepository;
        this.clientRepository = clientRepository;
        this.invoiceTokenManager = invoiceTokenManager;
    }

    @Override
    public BookingEntry add(BookingEntry bookingEntry) throws Exception {
        validateRequest(bookingEntry);
        if (!branchRepository.existsById(bookingEntry.getBranchId())) {
            throw new EntityNotFoundException("Branch not found");
        }
        if (!clientRepository.existsById(bookingEntry.getClientEntry().getClientId())) {
            throw new EntityNotFoundException("Client not found");
        }

        Booking booking = BookingConvertor.convertToEntity(bookingEntry, null);
        booking = bookingRepository.save(booking);

        InvoiceToken token = invoiceTokenManager.addInvoiceToken(booking);
        booking.setInvoiceToken(token);
        try {
            Long payeeId = booking.getId();
            if (bookingEntry.getPaymentEntries() == null || bookingEntry.getPaymentEntries().isEmpty()) {
                throw new EntityNotFoundException("Payment details not provided");
            }
            for (PaymentEntry pe : bookingEntry.getPaymentEntries()) {
                pe.setPayeeType(PayeeType.BOOKING);
                pe.setPayeeId(payeeId);
                PaymentEntry payment = paymentManager.add(pe);
                booking.getPayments().add(PaymentConvertor.convertToEntity(payment, (PaymentBooking) null));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new EntityNotFoundException(
                    ex.getMessage() != null ? "Failed to add payment details: " + ex.getMessage()
                            : "Failed to add payment details");
        }
        return BookingConvertor.convertToEntry(booking);
    }

    private void validateRequest(BookingEntry bookingEntry) {
        if (Objects.isNull(bookingEntry.getTotalAmount()) || bookingEntry.getTotalAmount() <= 0) {
            throw new IllegalArgumentException("Total amount must be greater than zero");
        }
    }

    @Override
    public BookingEntry update(Long bookingId, BookingEntry bookingEntry) throws Exception {
        validateRequest(bookingEntry);
        return super.update(bookingId, bookingEntry);
    }

    public Page<BookingEntry> getAllBookings(Long branchId, Integer page, Integer size,
            Integer startDate, Integer startMonth, Integer startYear,
            Integer endDate, Integer endMonth, Integer endYear,
            String searchTerm, String paymentType) throws Exception {
        Pageable pageable = size == -1
                ? Pageable.unpaged()
                : PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "lastModifiedOn"));

        Specification<Booking> spec = Specification.where(BookingSpecifications.hasBranchId(branchId))
                .and(BookingSpecifications.search(searchTerm))
                .and(BookingSpecifications.byPaymentType(paymentType));

        if ((startDate != null && startDate > 0) && (endDate != null && endDate > 0)) {
            Map<String, Date> dateRange = DateUtil.getUTCDateRange(startDate, startMonth, startYear, endDate, endMonth,
                    endYear);
            spec = spec.and(BookingSpecifications.createdOnBetween(dateRange.get("start"), dateRange.get("end")));
        } else if (startMonth != null && startMonth > 0 && endMonth != null && endMonth > 0 &&
                startYear != null && startYear > 0 && endYear != null && endYear > 0) {
            Map<String, Date> monthRange = DateUtil.getDateRangeByMonthYear(startMonth, startYear, endMonth, endYear);
            spec = spec.and(BookingSpecifications.createdOnBetween(monthRange.get("start"), monthRange.get("end")));
        }

        Page<Booking> pageResult = bookingRepository.findAll(spec, pageable);

        return pageResult.map(t -> {
            try {
                return BookingConvertor.convertToEntry(t);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    @Override
    protected Booking toEntity(BookingEntry entry, Booking existing)
            throws EntityNotFoundException, BeansException, Exception {
        return BookingConvertor.convertToEntity(entry, existing);
    }

    @Override
    protected BookingEntry toEntry(Booking entity, String[] fields) throws EntityNotFoundException {
        return BookingConvertor.convertToEntry(entity);
    }

    public InvoiceTokenResponse getInvoiceDataByToken(String token) throws Exception {
        return invoiceTokenManager.getInvoiceDataByToken(token);
    }
}
