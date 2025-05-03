package com.dancestudio.erp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Attendance extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member student;

    @ManyToOne
    @JoinColumn(name = "activity_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_activity_id"))
    private Activity activity;

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_attendance_branch_id"))
    private Branch branch;

}

