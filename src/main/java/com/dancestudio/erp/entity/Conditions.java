package com.dancestudio.erp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "conditions", uniqueConstraints = {
        @UniqueConstraint(name = "entityType_branch_key", columnNames = {"entityType", "branch_id"})
})
public class Conditions extends BaseEntity {

    @Column(name = "entityType", nullable = false)
    private String entityType;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_condition_branch_id"))
    private Branch branch;

}
