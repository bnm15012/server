package com.dancestudio.erp.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Payment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studio_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_payment_studio_id"))
    private Studio studio;

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

