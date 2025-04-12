package com.dancestudio.erp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class InstructorActivityAssignment extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "instructor_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_instructor_id"))
    private Instructor instructor;

    @ManyToOne
    @JoinColumn(name = "activity_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_iaa_activity_id"))
    private Activity activity;

    private Date assignedDate;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

}
