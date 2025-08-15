package com.dancestudio.erp.entity.activity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

import com.dancestudio.erp.entity.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "activity_membership_plan")
public class ActivityMembershipPlan extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "activity_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_activity_membership_plan_activity_id")
    )
    private Activity activity;

    @Column(name = "planType", nullable = false)
    private String planType;

    @Column(name = "daysPerWeek")
    private Integer daysPerWeek;

    @OneToMany(mappedBy = "membershipPlan", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ActivityBatch> batches = new ArrayList<>();
}
