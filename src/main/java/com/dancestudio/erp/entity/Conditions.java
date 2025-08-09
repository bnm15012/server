package com.dancestudio.erp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "conditions", uniqueConstraints = {
        @UniqueConstraint(name = "activityType_entityType_branch_key", columnNames = {"activity_type", "entity_type", "branch_id"})
})
public class Conditions extends BaseEntity {

    @Column(name = "entity_type", nullable = false)
    private String entityType;

    @Column(name = "activity_type", nullable = false)
    private String activityType;

    @Column(name = "template_name", nullable = false)
    private String templateName;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_condition_branch_id"))
    private Branch branch;

}
