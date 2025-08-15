package com.dancestudio.erp.entity.activity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

import com.dancestudio.erp.entity.BaseEntity;
import com.dancestudio.erp.entity.Branch;

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

    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ActivityMembershipPlan> membershipPlan = new ArrayList<>();
}
