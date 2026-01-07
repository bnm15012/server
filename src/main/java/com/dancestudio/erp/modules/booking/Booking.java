package com.dancestudio.erp.modules.booking;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dancestudio.erp.entity.BaseEntity;
import com.dancestudio.erp.entity.Branch;
import com.dancestudio.erp.modules.payments.entity.PaymentBooking;
import com.dancestudio.erp.modules.client.Client;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Booking extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_booking_branch_id"))
    private Branch branch;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_booking_client_id"))
    private Client client;

    @Column(name = "purpose", columnDefinition = "TEXT", nullable = false)
    private String purpose;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @Column(name = "payment_status", nullable = false)
    private String paymentStatus;

    @Column(name = "paid_amount", nullable = false)
    private Double paidAmount;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "booking_date", nullable = false)
    private Date bookingDate;

    @Column(name = "start_time", nullable = false)
    private Date startTime;

    @Column(name = "end_time", nullable = false)
    private Date endTime;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentBooking> payments = new ArrayList<>();

}
