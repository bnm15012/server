package com.dancestudio.erp.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Payment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_payment_branch_id"))
    private Branch branch;

    @Column(name = "payee_type", nullable = false)
    private String payeeType;

    private Long payeeId;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "actual_amount")
    private Double actualAmount;

    private Date paymentDate;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "payment_type", nullable = false)
    private String paymentType;

}

