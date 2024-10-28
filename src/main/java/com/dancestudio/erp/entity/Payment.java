package com.dancestudio.erp.entity;


import com.dancestudio.erp.enums.PayeeType;
import com.dancestudio.erp.enums.PaymentStatus;
import com.dancestudio.erp.enums.PaymentType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    private PayeeType payeeType;
    private Long payeeId;
    private Double amount;
    private LocalDate paymentDate;
    private PaymentStatus status;
    private PaymentType paymentType;
}

