package com.dancestudio.erp.entity.activity;

import com.dancestudio.erp.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "activity_batch")
public class ActivityBatch extends BaseEntity {

    @Column(name = "price", nullable = false)
    private Double price;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "startTime", nullable = false)
    private String startTime;
    
    @Column(name = "endTime", nullable = false)
    private String endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_membership_plan_id", nullable = false, foreignKey = @ForeignKey(name = "fk_activity_batch_activity_membership_plan_id"))
    private ActivityMembershipPlan membershipPlan;
}
