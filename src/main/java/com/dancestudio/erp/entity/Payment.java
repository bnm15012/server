package com.dancestudio.erp.entity;


import com.dancestudio.erp.enums.PayeeType;
import com.dancestudio.erp.enums.PaymentStatus;
import com.dancestudio.erp.enums.PaymentType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Payment extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "studio_id")
    private Studio studio;

    @Enumerated(EnumType.STRING)
    @Column(name = "payee_type", nullable = false)
    private PayeeType payeeType;

    private Long payeeId;

    private Double amount;

    private LocalDate paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type", nullable = false)
    private PaymentType paymentType;

}

