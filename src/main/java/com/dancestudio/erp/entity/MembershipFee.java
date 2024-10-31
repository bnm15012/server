package com.dancestudio.erp.entity;

import com.dancestudio.erp.enums.MembershipType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class MembershipFee extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "studio_id", nullable = false)
    private Studio studio;

    private MembershipType membershipType;

    private Double feeAmount;

}
