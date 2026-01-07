package com.dancestudio.erp.modules.payments.entity;


import com.dancestudio.erp.modules.booking.Booking;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.ForeignKey;
import lombok.Data;

@Entity
@Data
public class PaymentBooking {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @MapsId
    @JoinColumn(name = "id", foreignKey = @ForeignKey(name = "fk_payment_booking_payment_id"))
    private Payment payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_payment_booking_booking_id"))
    private Booking booking;
}
