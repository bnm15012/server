package com.dancestudio.erp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "activity", uniqueConstraints = {
        @UniqueConstraint(name = "activityType_studio_key", columnNames = {"activityType", "studio_id"})
})
public class Activity extends BaseEntity {

    @Column(name = "activityType", nullable = false)
    private String activityType;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studio_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_activity_studio_id"))
    private Studio studio;

    @Column(name = "membership_plans", columnDefinition = "json")
    private String membershipPlans;
}
