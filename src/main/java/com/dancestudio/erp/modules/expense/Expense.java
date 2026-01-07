package com.dancestudio.erp.modules.expense;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;

import com.dancestudio.erp.entity.BaseEntity;
import com.dancestudio.erp.entity.Branch;

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
    @CreatedDate
    @Column(name = "expense_date", nullable = false, updatable = false)
    private Date expenseDate;

}
