package com.dancestudio.erp.modules.invoiceToken;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.UUID;

import com.dancestudio.erp.modules.booking.Booking;
import com.dancestudio.erp.modules.member.student.StudentActivityAssignment.StudentActivityAssignment;

@Entity
@Table(name = "invoice_token")
@Data
public class InvoiceToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "token", columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID invoiceToken;

    @OneToOne
    @JoinColumn(name = "student_assignment_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_student_assignment_id"))
    private StudentActivityAssignment studentActivityAssignment;

    @OneToOne
    @JoinColumn(name = "booking_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_booking_id"))
    private Booking booking;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "expires_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiresAt;
}
