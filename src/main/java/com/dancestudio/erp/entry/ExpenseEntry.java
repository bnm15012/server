package com.dancestudio.erp.entry;

import com.dancestudio.erp.enums.ExpenseCategory;
import lombok.Data;

import java.util.Date;

@Data
public class ExpenseEntry {

    private Long expenseId;
    private Double amount;
    private String description;
    private Long studioId;
    private Date expenseDate;
    private ExpenseCategory expenseCategory;

}
