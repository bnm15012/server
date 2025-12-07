package com.dancestudio.erp.modules.payments.entity;

import com.dancestudio.erp.modules.member.student.StudentActivityAssignment;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.ForeignKey;
import lombok.Data;

@Entity
@Data
public class PaymentStudentActivity {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id", foreignKey = @ForeignKey(name = "fk_payment_member_payment_id"))
    private Payment payment;

    private Double actualAmount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_activity_assignment", nullable = false, unique = true, // IMPORTANT
            foreignKey = @ForeignKey(name = "fk_payment_student_activity_assignment_id"))
    private StudentActivityAssignment studentActivityAssignment;
}
