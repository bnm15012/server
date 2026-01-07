package com.dancestudio.erp.modules.payments.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

import com.dancestudio.erp.entity.BaseEntity;
import com.dancestudio.erp.entity.Branch;
import com.dancestudio.erp.enums.PaymentStatus;
import com.dancestudio.erp.enums.PaymentType;
import com.dancestudio.erp.modules.payments.enums.TransactionType;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Payment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_payment_branch_id"))
    private Branch branch;

    @Column(name = "amount")
    private Double amount;

    private Date paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type", nullable = false)
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    @OneToOne(mappedBy = "payment")
    private PaymentBooking paymentBooking;

    @OneToOne(mappedBy = "payment")
    private PaymentStudentActivity paymentStudentActivity;

}
