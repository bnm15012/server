package com.dancestudio.erp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class MembershipFee extends BaseEntity {

    @Column(name = "studio_id", nullable = false)
    private Long studioId;

    private String membershipType;

    private Double feeAmount;

}
