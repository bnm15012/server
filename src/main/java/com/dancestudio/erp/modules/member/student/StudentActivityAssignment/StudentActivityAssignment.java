package com.dancestudio.erp.modules.member.student.StudentActivityAssignment;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

import com.dancestudio.erp.entity.BaseEntity;
import com.dancestudio.erp.enums.ActivityType;
import com.dancestudio.erp.modules.member.Member;
import com.dancestudio.erp.modules.payments.entity.PaymentStudentActivity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(indexes = {
    @Index(name = "idx_student_activity_search", columnList = "activity_name, batch_name, batch_time")
})
public class StudentActivityAssignment extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "activity_name", nullable = false, length = 100)
    private ActivityType activityName;

    @Column(name = "registration_date", nullable = false)
    private Date registrationDate;

    @Column(name = "membership_start_date", nullable = false)
    private Date membershipStartDate;

    @Column(name = "membership_end_date", nullable = false)
    private Date membershipEndDate;

    @Column(name = "membership_type", nullable = false)
    private String membershipType;

    @Column(name = "activity_amount", nullable = false)
    private Double activityAmount;

    @Column(name = "days_per_week", nullable = false)
    private Integer daysPerWeek;

    @Column(name = "batch_name", nullable = false)
    private String batchName;

    @Column(name = "batch_time", nullable = false)
    private String batchTime;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_student_id"))
    private Member student;

    @OneToOne(mappedBy = "studentActivityAssignment", cascade = CascadeType.ALL, orphanRemoval = true)
    private PaymentStudentActivity payment;

    @Column(columnDefinition = "VARBINARY(48)", name = "attendance")
    private byte[] attendanceBitmap;
}
