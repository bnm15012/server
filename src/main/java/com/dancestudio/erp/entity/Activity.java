package com.dancestudio.erp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "activity", uniqueConstraints = {
        @UniqueConstraint(name = "activityType_branch_key", columnNames = {"activityType", "branch_id"})
})
public class Activity extends BaseEntity {

    @Column(name = "activityType", nullable = false)
    private String activityType;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_activity_branch_id"))
    private Branch branch;

    @Column(name = "membership_plans", columnDefinition = "json")
    private String membershipPlans;
}
