package com.dancestudio.erp.entity.activity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import com.dancestudio.erp.entity.BaseEntity;
import com.dancestudio.erp.entity.Studio;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "activity_membership_type", uniqueConstraints = {
        @UniqueConstraint(name = "membership_type_studio_key", columnNames = {"membership_type", "studio_id"})
})
public class ActivityMembershipType extends BaseEntity {

    @Column(name = "membership_type", nullable = false)
    private String membershipType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "studio_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_activity_membership_type_studio_id"))
    private Studio studio;
}
