package com.dancestudio.erp.modules.booking;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.dancestudio.erp.entity.Branch;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.modules.client.Client;
import com.dancestudio.erp.modules.client.ClientRepository;
import com.dancestudio.erp.modules.payments.PaymentConvertor;
import com.dancestudio.erp.repository.BranchRepository;
import jakarta.annotation.PostConstruct;
import com.dancestudio.erp.modules.client.ClientConvertor;

@Component
public class BookingConvertor {
    private static ApplicationContext applicationContext;

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void init() {
        applicationContext = context;
    }

    public static BookingEntry convertToEntry(Booking booking) {
        BookingEntry bookingEntry = new BookingEntry();

        bookingEntry.setId(booking.getId());
        bookingEntry.setBranchId(booking.getBranch().getId());
        bookingEntry.setPurpose(booking.getPurpose());
        bookingEntry.setTotalAmount(booking.getTotalAmount());
        bookingEntry.setState(booking.getBookingStatus());
        bookingEntry.setNotes(booking.getNotes());
        bookingEntry.setBookingDate(booking.getBookingDate());
        bookingEntry.setStartTime(booking.getStartTime());
        bookingEntry.setEndTime(booking.getEndTime());
        if (Objects.nonNull(booking.getInvoiceToken())) {
            bookingEntry.setInvoiceToken(booking.getInvoiceToken().getInvoiceToken().toString());
        }
        if (Objects.nonNull(booking.getClient())) {
            bookingEntry.setClientEntry(ClientConvertor.convertToEntry(booking.getClient()));
        }
        bookingEntry.setPaymentEntries(booking.getPayments().stream().map(PaymentConvertor::convertToEntry).toList());
        return bookingEntry;
    }

    public static Booking convertToEntity(BookingEntry bookingEntry, Booking existingBooking) throws Exception {
        Booking booking = (existingBooking != null) ? existingBooking : new Booking();

        if (Objects.nonNull(bookingEntry.getBranchId())) {
            BranchRepository branchRepository = applicationContext.getBean(BranchRepository.class);
            Branch branch = branchRepository.findById(bookingEntry.getBranchId())
                    .orElseThrow(() -> new EntityNotFoundException("Branch not found"));

            booking.setBranch(branch);
        }
        if (Objects.nonNull(bookingEntry.getClientEntry())) {

            ClientRepository clientRepository = applicationContext.getBean(ClientRepository.class);
            Client client = clientRepository.findById(bookingEntry.getClientEntry().getClientId())
                    .orElseThrow(() -> new EntityNotFoundException("Client not found"));
            ;
            booking.setClient(client);
        }

        Optional.ofNullable(bookingEntry.getPurpose()).ifPresent(booking::setPurpose);
        Optional.ofNullable(bookingEntry.getTotalAmount()).ifPresent(booking::setTotalAmount);
        Optional.ofNullable(bookingEntry.getState()).ifPresent(booking::setBookingStatus);
        Optional.ofNullable(bookingEntry.getNotes()).ifPresent(booking::setNotes);
        Optional.ofNullable(bookingEntry.getBookingDate()).ifPresent(booking::setBookingDate);
        Optional.ofNullable(bookingEntry.getStartTime()).ifPresent(booking::setStartTime);
        Optional.ofNullable(bookingEntry.getEndTime()).ifPresent(booking::setEndTime);
        return booking;
    }
}
