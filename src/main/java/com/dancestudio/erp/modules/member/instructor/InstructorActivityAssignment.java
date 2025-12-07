package com.dancestudio.erp.modules.member.instructor;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

import com.dancestudio.erp.entity.BaseEntity;
import com.dancestudio.erp.modules.member.Member;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class InstructorActivityAssignment extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "instructor_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_instructor_id"))
    private Member instructor;

    @Column(name = "activity_name", nullable = false, length = 100)
    private String activityName;

    private Date assignedDate;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "contract_document")
    private String contractDocument;

}
