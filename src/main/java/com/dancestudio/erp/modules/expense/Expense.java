package com.dancestudio.erp.modules.expense;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import com.dancestudio.erp.entity.BaseEntity;
import com.dancestudio.erp.entity.Branch;
import com.dancestudio.erp.enums.PaymentType;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Expense extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_expense_branch_id"))
    private Branch branch;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "expense_category", nullable = false)
    private String expenseCategory;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expense_date", nullable = false)
    private Date expenseDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type", nullable = false)
    private PaymentType paymentType;
}
