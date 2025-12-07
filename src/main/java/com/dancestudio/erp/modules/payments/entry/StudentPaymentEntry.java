package com.dancestudio.erp.modules.payments.entry;

import com.dancestudio.erp.modules.member.student.StudentActivityAssignment;

import lombok.Data;

@Data
public class StudentPaymentEntry {
    private Long id;
    private PaymentEntry paymentEntry;
    private StudentActivityAssignment studentActivityAssignment;
}
