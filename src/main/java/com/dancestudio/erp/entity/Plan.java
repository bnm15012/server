package com.dancestudio.erp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Plan extends BaseEntity {

    @Column(name = "plan_type", nullable = false)
    private String planType;

    @Column(name = "enabled_features")
    private String enabledFeatures;

    @Column(name = "disabled_features")
    private String disabledFeatures;

    @Column(name = "sms_quota", nullable = false)
    private double smsQuota = 0;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "country_code", nullable = false)
    private String countryCode;

    @Column(name = "popular", nullable = false)
    private Boolean popular;

    @Column(name = "description", nullable = false)
    private String description;
}
