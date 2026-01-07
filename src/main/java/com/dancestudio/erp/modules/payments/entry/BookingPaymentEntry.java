package com.dancestudio.erp.modules.payments.entry;

import java.util.List;

import com.dancestudio.erp.modules.booking.BookingEntry;
import com.dancestudio.erp.modules.client.ClientEntry;
import lombok.Data;

@Data
public class BookingPaymentEntry {
    private Long id;
    private BookingEntry bookingEntry;
    private ClientEntry clientEntry;
    private List<PaymentEntry> payments;
}
