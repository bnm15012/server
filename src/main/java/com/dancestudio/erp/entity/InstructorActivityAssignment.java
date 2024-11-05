package com.dancestudio.erp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class InstructorActivityAssignment extends BaseEntity {

    @Column(name = "instructor_id")
    private Long instructorId;

    @Column(name = "activity_id", nullable = false)
    private Long activityId;

    private LocalDate assignedDate;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "status", nullable = false)
    private String status;

}
