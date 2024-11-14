package com.dancestudio.erp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Expense extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studio_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_expense_studio_id"))
    private Studio studio;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(name = "expense_date", nullable = false, updatable = false, insertable = true, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date expenseDate;

    private String expenseCategory;

}
