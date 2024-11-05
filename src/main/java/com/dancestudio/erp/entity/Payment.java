package com.dancestudio.erp.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Payment extends BaseEntity {

    @Column(name = "studio_id")
    private Long studioId;

    @Column(name = "payee_type", nullable = false)
    private String payeeType;

    private Long payeeId;

    private Double amount;

    private LocalDate paymentDate;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "payment_type", nullable = false)
    private String paymentType;

}

